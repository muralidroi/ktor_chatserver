package com.murali.route

import com.murali.Room.MemberAlreadyExistException
import com.murali.Room.RoomController
import com.murali.session.ChatSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(roomController: RoomController) {
    webSocket ("/chat-socket"){
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No Session"))
        }
        if (session != null) {
            try {
                roomController.onJoin(
                    userName = session.userName,
                    sessionId = session.sessionId,
                    socket = this
                )
                incoming.consumeEach { frame ->
                    if(frame is Frame.Text) {
                        roomController.sendMessage(
                            senderName = session.userName,
                            message = frame.readText()
                        )
                    }
                }
            } catch (e : MemberAlreadyExistException) {
                call.respond(
                    HttpStatusCode.Conflict
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                roomController.tryDisconnect(session.userName)
            }
        }
    }
}

fun Route.getAllMessages(roomController: RoomController) {
    get("/messages") {
        call.respond(HttpStatusCode.OK)
        roomController.getAllMessages()
    }
}