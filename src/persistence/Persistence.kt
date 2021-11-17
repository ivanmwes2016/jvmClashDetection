package persistence

import data_classes.Program
import data_classes.Module

abstract class Persistence {
    companion object{
        /*** To append Items to the database*/
        fun createDBPersistence(): DBPersistence {
            return DBPersistence()
        }
    }

    abstract fun loadPrograms(): ArrayList<String> /* called on run time to load all the programs from the database */
    abstract fun saveProgram(id: String, name: String, type: String): Boolean /* called whenever user saves/creates a new program. */
    abstract fun updateProgram(program: Program, currentPid:String) //called whenever a program is edited.
    abstract fun deleteProgram(pid: String)//called whenever a program is deleted.
    abstract fun deleteAllPrograms() //called when user deletes all programs
    abstract fun loadModules(pid:String): ArrayList<String> //called when user selects a program to load all the associated modules.
    abstract fun saveModule(pid: String, mid: String, name: String, type:String, year:String, term:String): Boolean//called when user saves/creates a new module.
    abstract fun updateModule(module: Module, currentMid:String)
    abstract fun deleteModule(mid: String) // called whenever user deletes a module.
    abstract fun deleteAllModules(pid: String) //called whenever user deletes all modules.

}