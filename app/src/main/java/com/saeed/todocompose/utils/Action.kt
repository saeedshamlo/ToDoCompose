package com.saeed.todocompose.utils

enum class Action {

    ADD,
    UPDATE,
    DELETE_ALL,
    DELETE,
    UNDO,
    NO_ACTION
}

fun String?.toAction():Action{
    return when{
        this == "ADD" ->{
            Action.ADD
        }
        this == "UPDATE" ->{
            Action.UPDATE
        }
        this == "DELETE" ->{
            Action.DELETE
        }
        this == "DELETE_ALL" ->{
            Action.DELETE_ALL
        }
        this == "UNDO" ->{
            Action.UNDO
        }
        else ->{
            Action.NO_ACTION
        }
    }
}