package ie.setu.orderreceiver.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.setu.orderreceiver.data.AppDatabase
import ie.setu.orderreceiver.data.dao.MenuDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideMenuDao(appDatabase: AppDatabase): MenuDao {
        return appDatabase.menuDao()
    }
}