package com.murali.data

import com.murali.data.model.Message

interface MessageDataSource {

    suspend fun getAllMessages() : List<Message>

    suspend fun insertMessage(message: Message)

}