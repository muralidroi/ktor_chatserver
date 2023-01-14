package com.murali.di

import com.murali.Room.RoomController
import com.murali.data.MessageDataSource
import com.murali.data.MessageDataSourceImpl
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine

val mainModule = module {
    single {
        org.litote.kmongo.reactivestreams.KMongo.createClient()
            .coroutine
            .getDatabase("message_db_yt")
    }
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }
    single {
        RoomController(get())
    }
}