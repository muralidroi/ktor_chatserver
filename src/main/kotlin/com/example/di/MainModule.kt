package com.example.di

import com.example.Room.RoomController
import com.example.data.MessageDataSource
import com.example.data.MessageDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import org.litote.kmongo.KMongo
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