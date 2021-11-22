package com.example.vinilos.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilos.models.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://protected-ravine-18753.herokuapp.com/"
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun getAlbums() = suspendCoroutine<List<Album>>{cont->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                cont.resume(list)
            },
            Response.ErrorListener{
                cont.resumeWithException(it)
            }))
    }

    suspend fun getAlbum (albumId:Int) = suspendCoroutine<AlbumDetail> {cont->
        requestQueue.add(getRequest("albums/$albumId",
            Response.Listener<String>{ response ->
                val resp = JSONObject(response)
                val tracks = resp.getJSONArray("tracks")
                val performers = resp.getJSONArray("performers")
                val comments = resp.getJSONArray("comments")
                val trackList = mutableListOf<Track>()
                val performerList = mutableListOf<Performer>()
                val commentList = mutableListOf<Comment>()
                for (i in 0 until tracks.length()) {
                    val item = tracks.getJSONObject(i)
                    trackList.add(i, Track(trackId = item.getInt("id"),name = item.getString("name"), duration = item.getString("duration")))
                }
                for (i in 0 until performers.length()) {
                    val item = performers.getJSONObject(i)
                    performerList.add(i, Performer(performerId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate")))
                }
                for (i in 0 until comments.length()) {
                    val item = comments.getJSONObject(i)
                    commentList.add(i, Comment(commentId = item.getInt("id"),description = item.getString("description"), rating = item.getString("rating")))
                }
                val album = AlbumDetail(albumId = resp.getInt("id"),name = resp.getString("name"), cover = resp.getString("cover"), recordLabel = resp.getString("recordLabel"), releaseDate = resp.getString("releaseDate"), genre = resp.getString("genre"), description = resp.getString("description"), tracks = trackList, performers = performerList, comments = commentList)

                cont.resume(album)
            },
            Response.ErrorListener{
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
        requestQueue.add(getRequest("collectors",
            Response.Listener<String>{ response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(id = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email")))
                }
                cont.resume(list)
            },
            Response.ErrorListener{
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollector(collectorId:Int) = suspendCoroutine<CollectorDetail> {cont->
        requestQueue.add(getRequest("collectors/$collectorId",
            Response.Listener<String>{ response ->
                val resp = JSONObject(response)

                val performers = resp.getJSONArray("favoritePerformers")
                val comments = resp.getJSONArray("comments")

                val performerList = mutableListOf<Performer>()
                val commentList = mutableListOf<Comment>()

                for (i in 0 until performers.length()) {
                    val item = performers.getJSONObject(i)
                    performerList.add(i, Performer(performerId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate")))
                }
                for (i in 0 until comments.length()) {
                    val item = comments.getJSONObject(i)
                    commentList.add(i, Comment(commentId = item.getInt("id"),description = item.getString("description"), rating = item.getString("rating")))
                }
                val collector = CollectorDetail(collectorId = resp.getInt("id"),name = resp.getString("name"), telephone = resp.getString("telephone"), email = resp.getString("email"), comments = commentList, favoritePerformers = performerList)

                cont.resume(collector)
            },
            Response.ErrorListener{
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollectorAlbums (collectorId:Int) = suspendCoroutine<List<CollectorAlbums>>{cont->
        requestQueue.add(getRequest("collectors/$collectorId/albums",
            Response.Listener<String>{ response ->
                val resp = JSONArray(response)
                val list = mutableListOf<CollectorAlbums>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    val albumData = item.getJSONObject("album")
                    val collectorData = item.getJSONObject("collector")
                    val album = Album(albumId = albumData.getInt("id"),name = albumData.getString("name"), cover = albumData.getString("cover"), recordLabel = albumData.getString("recordLabel"), releaseDate = albumData.getString("releaseDate"), genre = albumData.getString("genre"), description = albumData.getString("description"))
                    val collector = Collector(id = collectorData.getInt("id"),name = collectorData.getString("name"), telephone = collectorData.getString("telephone"), email = collectorData.getString("email"))
                    list.add(i, CollectorAlbums(id = item.getInt("id"),price = item.getDouble("price"), status = item.getString("status"), album = album, collector =collector))
                }
                cont.resume(list)
            },
            Response.ErrorListener{
                cont.resumeWithException(it)
            }))
    }

    suspend fun postCollectorAlbum (body: JSONObject, collectorId: Int, albumId: Int) = suspendCoroutine<JSONObject> {cont->

        requestQueue.add(postRequest("collectors/$collectorId/albums/$albumId",
            body,
            Response.Listener<JSONObject> { response ->
                cont.resume(response)
            },
            Response.ErrorListener {

                cont.resumeWithException(it)
            }))
    }



    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}