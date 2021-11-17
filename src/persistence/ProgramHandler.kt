package persistence

import data_classes.*

class ProgramHandler{

    private val persistence = Persistence.createDBPersistence()

    val saveProgram: (String, String, String)
        -> Boolean = {id:String, name:String, type:String
            -> persistence.saveProgram(id, name, type) }

    val saveModule: (String, String, String, String, String, String)
        -> Boolean = {pid: String, mid: String, type:String,
            name: String, year:String, term:String
                -> persistence.saveModule(pid, mid, name, type, year, term) }

    val loadPrograms: ()
        -> ArrayList<String> = { persistence.loadPrograms() }

    val loadModules: (String)
        -> ArrayList<String> = { pid: String
            -> persistence.loadModules(pid) }

    val updateProgram: (Program, String)
        -> Unit = { program:Program, currentPid:String
            -> persistence.updateProgram(program, currentPid) }

    val updateModule: (Module, String)
        -> Unit = { module: Module, currentMid:String
            -> persistence.updateModule(module, currentMid) }

    val deleteProgram: (String)
        -> Unit = { pid:String
            -> persistence.deleteProgram(pid) }

    val deleteModule: (String)
        -> Unit = { pid:String
            -> persistence.deleteModule(pid) }

    val deleteAllPrograms: ()
        -> Unit = { persistence.deleteAllPrograms() }


    val deleteAllModules: (String)
        -> Unit = { pid: String
            -> persistence.deleteAllModules(pid) }





}