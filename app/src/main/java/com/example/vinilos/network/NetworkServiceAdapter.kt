package com.example.vinilos.network

import android.content.Context
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
    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description")))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }

    fun getAlbum(albumId:Int, onComplete:(resp:AlbumDetail)->Unit, onError: (error:VolleyError)->Unit) {
        requestQueue.add(getRequest("albums/$albumId",
            { response ->
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

                onComplete(album)
            },
            {
                onError(it)
            }))
    }

    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(id = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email")))
                }
                onComplete(list)
            },
            {
                onError(it)
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