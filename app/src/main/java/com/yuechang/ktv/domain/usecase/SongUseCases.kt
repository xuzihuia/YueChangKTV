package com.yuechang.ktv.domain.usecase

import com.yuechang.ktv.data.repository.SongRepository
import com.yuechang.ktv.domain.model.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 获取热门歌曲
 */
class GetHotSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(): Flow<List<Song>> {
        return songRepository.getHotSongs().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}

/**
 * 获取新歌
 */
class GetNewSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(): Flow<List<Song>> {
        return songRepository.getNewSongs().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}

/**
 * 获取免费歌曲
 */
class GetFreeSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(): Flow<List<Song>> {
        return songRepository.getFreeSongs().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}

/**
 * 按分类获取歌曲
 */
class GetSongsByCategoryUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(categoryId: String): Flow<List<Song>> {
        return songRepository.getSongsByCategory(categoryId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}

/**
 * 搜索歌曲
 */
class SearchSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(keyword: String): Flow<List<Song>> {
        return songRepository.searchSongs(keyword).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }
}

/**
 * 刷新歌曲列表
 */
class RefreshSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return songRepository.refreshHotSongs()
    }
}

// Entity 转 Domain Model
private fun com.yuechang.ktv.data.local.entity.SongEntity.toDomainModel() = Song(
    songId = songId,
    name = name,
    artist = artist,
    album = album,
    coverUrl = coverUrl,
    audioUrl = audioUrl,
    videoUrl = videoUrl,
    lyricsUrl = lyricsUrl,
    accompanyUrl = accompanyUrl,
    duration = duration,
    categoryId = categoryId,
    tags = tags,
    vipRequired = vipRequired,
    playCount = playCount
)