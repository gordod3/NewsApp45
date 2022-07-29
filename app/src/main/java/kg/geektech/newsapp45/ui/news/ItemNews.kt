package kg.geektech.newsapp45.ui.news

import java.io.Serializable
import java.time.temporal.TemporalAccessor

data class ItemNews(var title : String, val time : TemporalAccessor) : Serializable
