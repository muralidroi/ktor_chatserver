package com.example.di

import com.example.Room.RoomController
import com.example.data.MessageDataSource
import com.example.data.MessageDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import org.litote.kmongo.KMongo

val mainModule = module {
    single {
        KMongo.createClient()
            .getDatabase("message_db")
    }

    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }

    single {
        RoomController(get())
    }

}