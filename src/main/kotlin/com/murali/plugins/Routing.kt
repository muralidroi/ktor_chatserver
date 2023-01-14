package com.murali.plugins

import com.murali.Room.RoomController
import com.murali.route.chatSocket
import com.murali.route.getAllMessages
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.configureRouting() {
    val roomController by inject<RoomController>()
    install(Routing) {
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}