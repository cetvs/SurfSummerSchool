package com.example.myapplication.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapplication.domain.model.EntityPictureInfo
import com.example.myapplication.presentation.MainViewModel

@Composable
fun FavoriteScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    TopBarText(text = "Мои избранные")
    val state = remember {
        val list = mutableListOf<EntityPictureInfo>()
        for (el in mainViewModel.localState.value)
            if (el.FavoriteDate != "")
                list.add(el)
        return@remember mutableStateOf(list)
    }

    mainViewModel.getLocalPictureInfo()

    LazyRow() {
        items(
            count = state.value.size,
            itemContent = {
                MainGridListItem(state.value[it])
            }
        )
    }
}

@Composable
fun MainGridListItem(entityPictureInfo: EntityPictureInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        AsyncImage(
            model = entityPictureInfo.photoUrl,
            contentDescription = null,
            modifier = Modifier
                .size(326.dp, 326.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(entityPictureInfo.title)
            Text(entityPictureInfo.FavoriteDate)
        }
        Text(
            text = entityPictureInfo.content,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}