package com.yuechang.ktv.data.mock

import com.yuechang.ktv.data.remote.dto.*
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Mock 拦截器 - 拦截网络请求返回 Mock 数据
 */
class MockInterceptor : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        
        // 模拟网络延迟
        Thread.sleep(300)
        
        val responseBody = when {
            path.contains("/songs/hot") -> getHotSongsResponse()
            path.contains("/songs/new") -> getNewSongsResponse()
            path.contains("/songs/free") -> getFreeSongsResponse()
            path.contains("/songs/search") -> getSearchResponse(request.url.queryParameter("keyword"))
            path.contains("/home/categories") -> getCategoriesResponse()
            path.contains("/home/banners") -> getBannersResponse()
            path.contains("/user/profile") -> getUserProfileResponse()
            else -> getDefaultResponse()
        }
        
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(responseBody.toResponseBody("application/json".toMediaType()))
            .build()
    }
    
    private fun getHotSongsResponse(): String {
        val songs = MockData.getMockSongs().take(5).map {
            """{"songId":"${it.songId}","name":"${it.name}","artist":"${it.artist}","album":"${it.album}","coverUrl":"${it.coverUrl}","duration":${it.duration},"vipRequired":${it.vipRequired},"playCount":${it.playCount}}"""
        }
        return """{"code":0,"message":"success","data":[${songs.joinToString(",")}]}"""
    }
    
    private fun getNewSongsResponse(): String {
        val songs = MockData.getMockSongs().takeLast(3).map {
            """{"songId":"${it.songId}","name":"${it.name}","artist":"${it.artist}","album":"${it.album}","coverUrl":"${it.coverUrl}","duration":${it.duration},"vipRequired":${it.vipRequired},"playCount":${it.playCount}}"""
        }
        return """{"code":0,"message":"success","data":[${songs.joinToString(",")}]}"""
    }
    
    private fun getFreeSongsResponse(): String {
        val songs = MockData.getMockSongs().filter { !it.vipRequired }.take(4).map {
            """{"songId":"${it.songId}","name":"${it.name}","artist":"${it.artist}","album":"${it.album}","coverUrl":"${it.coverUrl}","duration":${it.duration},"vipRequired":false,"playCount":${it.playCount}}"""
        }
        return """{"code":0,"message":"success","data":[${songs.joinToString(",")}]}"""
    }
    
    private fun getSearchResponse(keyword: String?): String {
        val filtered = MockData.getMockSongs().filter {
            keyword?.let { k -> it.name.contains(k) || it.artist.contains(k) } ?: true
        }
        val songs = filtered.map {
            """{"songId":"${it.songId}","name":"${it.name}","artist":"${it.artist}","album":"${it.album}","coverUrl":"${it.coverUrl}","duration":${it.duration},"vipRequired":${it.vipRequired},"playCount":${it.playCount}}"""
        }
        return """{"code":0,"message":"success","data":[${songs.joinToString(",")}]}"""
    }
    
    private fun getCategoriesResponse(): String {
        val categories = MockData.getMockCategories().map {
            """{"categoryId":"${it.categoryId}","name":"${it.name}","icon":null,"sortOrder":${it.sortOrder},"songCount":${(10..100).random()}}"""
        }
        return """{"code":0,"message":"success","data":[${categories.joinToString(",")}]}"""
    }
    
    private fun getBannersResponse(): String {
        return """{"code":0,"message":"success","data":[{"bannerId":"1","title":"热门推荐","imageUrl":"https://example.com/banner/1.jpg","type":1},{"bannerId":"2","title":"VIP专享","imageUrl":"https://example.com/banner/2.jpg","type":2}]}"""
    }
    
    private fun getUserProfileResponse(): String {
        return """{"code":0,"message":"success","data":{"userId":"user_001","nickname":"测试用户","avatar":null,"phone":"138****8888","gender":1,"signature":"爱唱歌的人运气不会太差","vipLevel":1,"vipExpireTime":${System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000},"coins":1000,"beans":500,"fansCount":128,"followCount":56,"workCount":12}}"""
    }
    
    private fun getDefaultResponse(): String {
        return """{"code":0,"message":"success","data":null}"""
    }
}