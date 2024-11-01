package com.example.minemusic.Screen

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.nio.file.WatchEvent

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MusicListScreen(
    musicViewModel: MusicViewModel,//Не забыть!!!!!!!!!!!!!!!!!!
    onSelectedAudio : (musicState : MusicEvent) -> Unit //Не забыть!!!!!!!!!!!!!!!!!
) {

    val musicList by musicViewModel.musicState.collectAsStateWithLifecycle()
//    val playerState by musicViewModel.musicList.collectAsStateWithLifecycle()


    var permission = android.Manifest.permission.READ_EXTERNAL_STORAGE

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        permission = android.Manifest.permission.READ_MEDIA_AUDIO
    } else {
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val mediaPermissionState = rememberPermissionState(permission = permission){

    }

    if (mediaPermissionState.status.isGranted) {

        LaunchedEffect(key1 = true) {
            musicViewModel.loadFile()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Text(
                text = "My Music",
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)

            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(musicList.musicList.size) { index ->
                    val music = musicList.musicList[index]
                    Card(
                        onClick = {
                            onSelectedAudio( MusicEvent.Start(music))
                        },
                        colors = CardColors(
                            contentColor = Color.White,
                            containerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(text = Util.truncateName(music.name ?: ""))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = Util.calculateDuration(music.duration ?: 0L))
                            }
                            HorizontalDivider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
