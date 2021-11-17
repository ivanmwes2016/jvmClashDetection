package persistence

import data_classes.Module
import data_classes.Program
import java.awt.Frame
import java.sql.*
import java.sql.DriverManager.getConnection
import javax.swing.JOptionPane
import kotlin.collections.ArrayList

class DBPersistence (
    /** Database connection code */
    private val conn: Connection = getConnection("jdbc:sqlite:greenwich.db"),
    private val statement: Statement = conn.createStatement()

):Persistence()
{

    override fun saveProgram(id: String, name: String, type: String): Boolean {
        println("Saving $name to DB")
        try {
            statement.executeUpdate(
                "INSERT INTO `programs`(`pid`,`name`,`type`) " +
                    "VALUES('$id','$name','$type')")
            return true
        }catch(e: SQLIntegrityConstraintViolationException){
            JOptionPane.showMessageDialog(Frame(), "Duplicate entry $id, please enter a unique program ID.")
            return false
        }catch (e: SQLException){

            if(e.message?.contains("UNIQUE") == true){
                JOptionPane.showMessageDialog(Frame(), "Duplicate entry $id, please enter a unique program ID.")
            }else {
                JOptionPane.showMessageDialog(Frame(), "Oh my God!!..." + e.message)
            }
            return false
        }

    }

    override fun loadPrograms(): ArrayList<String>{

        val programs = ArrayList<String>()
        try {
            val getProgram= statement.executeQuery("SELECT * FROM `programs`")

            while (getProgram.next()){
                val name = getProgram.getString("name")
                val id = getProgram.getString("pid")
                val type = getProgram.getString("type")
                programs.add("[$id] $name ($type)")
            }

        } catch (e:SQLException){
            JOptionPane.showMessageDialog(Frame(), "Oh dear! there's a problem..." + e.message)
            println("Oh dear! there's a problem..." + e.message )
        }

        return programs
    }

    override fun updateProgram(program: Program, currentPid: String){

        try {

            val newPid = program.id
            val name = program.name
            val type = program.type

            statement.executeUpdate("UPDATE `programs` " +
                    "SET pid = \"$newPid\", name = \"$name\", type = \"$type\" WHERE pid = \"$currentPid\"")
            statement.executeUpdate("UPDATE `modules` " +
                    "SET pid = \"$newPid\" WHERE pid = \"$currentPid\"")

        } catch (e:SQLException){
            JOptionPane.showMessageDialog(Frame(), "Oh dear! there's a problem..." + e.message)
            println("Oh dear! there's a problem..." + e.message )
        }

    }

    override fun deleteProgram(pid: String){
        try {

            statement.executeUpdate("DELETE FROM `programs` WHERE pid = \"$pid\"")
            statement.executeUpdate("DELETE FROM `modules` WHERE pid = \"$pid\"")

        } catch (e:SQLException){
            JOptionPane.showMessageDialog(Frame(), "Oh dear! there's a problem..." + e.message)
            println("Oh dear! there's a problem..." + e.message )
        }
    }

    override fun deleteAllPrograms(){

        try {

            statement.executeUpdate("DELETE FROM `programs` ")
            statement.executeUpdate("DELETE FROM `modules` ")

        } catch (e:SQLException){
            JOptionPane.showMessageDialog(Frame(), "Oh dear! there's a problem..." + e.message)
            println("Oh dear! there's a problem..." + e.message )
        }

    }

    override fun saveModule(pid: String, mid: String, name: String, type:String, year:String, term:String) : Boolean{
        println("Saving $name to DB")
        try {
            statement.executeUpdate("INSERT INTO `modules`(`pid`,`mid`,`name`,`type`,`year`,`term`) " +
                    "VALUES('$pid','$mid','$name','$type','$year','$term')")
            return true
        }catch(e: SQLIntegrityConstraintViolationException){
            JOptionPane.showMessageDialog(Frame(), "Duplicate entry '$mid', please enter a unique module ID.")
            return false
        }catch (e: SQLException){

            if(e.message?.contains("UNIQUE") == true){
                JOptionPane.showMessageDialog(Frame(), "Duplicate entry $mid, please enter a unique module ID.")
            }else {
                JOptionPane.showMessageDialog(Frame(), "Oh my God!!..." + e.message)
            }
            println("Oh my God!!..." + e.message)
            return false
        }

    }

    override fun loadModules(pid:String): ArrayList<String>{

        val modules = ArrayList<String>()
        try {
            val getModules= statement.executeQuery("SELECT * FROM `modules` WHERE pid = \"$pid\"")
            while (getModules.next()){

                val mid = getModules.getString("mid")
                val name = getModules.getString("name")
                val type = getModules.getString("type")
                val year = getModules.getString("year")
                val term = getModules.getString("term")
                modules.add("[$pid-$mid] "+name+"_"+year+"_"+term+" ("+type+")")
            }

        } catch (e:SQLException){
            JOptionPane.showMessageDialog(Frame(), "Oh dear! there's a problem..." + e.message)
            println("Oh dear! there's a problem..." + e.message )
        }

        return modules
    }

    override fun updateModule(module: Module, currentMid: String) {

        try {

            val newMid = module.mid
            val name = module.name
            val type = module.type
            val term = module.term
            val year = module.year

            statement.executeUpdate("UPDATE `modules` " +
                    "SET mid = \"$newMid\", name = \"$name\", year = \"$year\", term = \"$term\", type = \"$type\"" +
                    "  WHERE mid = \"$currentMid\" ")

        } catch (e:SQLException){
            JOptionPane.showMessageDialog(Frame(), "Oh dear! there's a problem..." + e.message)
            println("Oh dear! there's a problem..." + e.message )
        }

    }

    override fun deleteModule(mid: String){

        try {

            statement.executeUpdate("DELETE FROM `modules` WHERE mid = \"$mid\"")

        } catch (e:SQLException){
            JOptionPane.showMessageDialog(Frame(), "Oh dear! there's a problem..." + e.message)
            println("Oh dear! there's a problem..." + e.message )
        }

    }

    override fun deleteAllModules(pid: String){

        try {

            statement.executeUpdate("DELETE FROM `modules` WHERE pid = \"$pid\"")

        } catch (e:SQLException){
            JOptionPane.showMessageDialog(Frame(), "Oh dear! there's a problem..." + e.message)
            println("Oh dear! there's a problem..." + e.message )
        }

    }


}