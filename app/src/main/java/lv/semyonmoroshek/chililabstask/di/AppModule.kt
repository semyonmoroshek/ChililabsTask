//package lv.semyonmoroshek.chililabstask.di
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import lv.semyonmoroshek.chililabstask.data.network.API
//import lv.semyonmoroshek.chililabstask.data.repository.Repository
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class AppModule {
//
//    @Singleton @Provides
//    fun repository(api: API) : Repository {
//        return Repository.getInstance(api)
//    }
//
//}