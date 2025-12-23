package dev.bltucker.vibeplayer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.bltucker.vibeplayer.di.IoDispatcher
import dev.bltucker.vibeplayer.home.TrackValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class VibePlayerApplication : Application() {

    @Inject
    lateinit var trackValidator: TrackValidator

    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    private val applicationScope by lazy { CoroutineScope(SupervisorJob() + ioDispatcher) }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            trackValidator.execute()
        }
    }
}
