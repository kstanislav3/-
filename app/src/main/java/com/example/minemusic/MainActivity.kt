package com.example.minemusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.minemusic.ui.theme.MineMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicListScreen(musicViewModel, onSelectedAudio = { musicEvent->

                musicViewModel.onAction(musicEvent)

                if (musicEvent is MusicEvent.SliderChange){
                    val value = musicEvent as MusicEvent.SliderChange
                    sendBroadcast(Intent(Util.SLIDER_STATE_CHANNEL).apply {
                        putExtra(Util.SLIDER_CHANGE_VALUE, value.duration)
                    })
                } else {
                    sendBroadcast(Intent(Util.PLAYER_STATE_CHANNEL).apply {
                        putExtra("player", musicViewModel.musicState.value.playerState)
                    })
                }
            })
        }
    }
    musicViewModel.setContentResolver(contentResolver)
}



