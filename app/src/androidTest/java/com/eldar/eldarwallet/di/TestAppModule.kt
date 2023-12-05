package com.eldar.eldarwallet.di

import android.content.Context
import androidx.room.Room
import com.eldar.eldarwallet.data.datasource.CardsData
import com.eldar.eldarwallet.data.datasource.GenerateQrData
import com.eldar.eldarwallet.data.datasource.UsersData
import com.eldar.eldarwallet.framework.datasource.cards.CardsDataImpl
import com.eldar.eldarwallet.framework.datasource.db.AppDataBase
import com.eldar.eldarwallet.framework.datasource.qr.GenerateQrDataImpl
import com.eldar.eldarwallet.framework.datasource.users.UsersDataImpl
import com.eldar.eldarwallet.framework.di.BaseModule
import com.eldar.eldarwallet.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BaseModule::class]
)
class TestAppModule {

    @Provides
    @Singleton
    fun databaseProvider(@ApplicationContext context: Context) : AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, Constants.dbName).build()
    }

    @Named("url_generate_qr")
    @Provides
    @Singleton
    fun urlHttpProvider() : String {
        return "http://localhost:8080"
    }

    @Provides
    @Singleton
    fun cardsDataProvider(@ApplicationContext context: Context, db: AppDataBase) : CardsData {
        return CardsDataImpl(context, db)
    }

    @Provides
    @Singleton
    fun generateQrDataProvider(@Named("url_generate_qr") url: String) : GenerateQrData {
        return GenerateQrDataImpl(url)
    }

    @Provides
    @Singleton
    fun usersDataProvider(@ApplicationContext context: Context, db: AppDataBase) : UsersData {
        return UsersDataImpl(context, db)
    }
}