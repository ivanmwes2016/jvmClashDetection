package data_classes

import data_classes.Module

data class Program(
    val id:String,
    val name:String,
    val type:String
){
    var modules = ArrayList<Module>()
}
