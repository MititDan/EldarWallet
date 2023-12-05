package com.eldar.eldarwallet.data.di

import com.eldar.eldarwallet.data.datasource.CardsData
import com.eldar.eldarwallet.data.datasource.GenerateQrData
import com.eldar.eldarwallet.data.datasource.UsersData
import com.eldar.eldarwallet.data.repository.CardsRepo
import com.eldar.eldarwallet.data.repository.GenerateQrRepo
import com.eldar.eldarwallet.data.repository.UsersRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {

    @Provides
    fun cardsRepoProvider(data: CardsData) : CardsRepo {
        return CardsRepo(data)
    }

    @Provides
    fun generateQrRepoProvider(data: GenerateQrData) : GenerateQrRepo {
        return GenerateQrRepo(data)
    }

    @Provides
    fun usersRepoProvider(data: UsersData) : UsersRepo {
        return UsersRepo(data)
    }
}