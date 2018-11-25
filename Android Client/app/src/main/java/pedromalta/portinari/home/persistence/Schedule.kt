package pedromalta.portinari.home.persistence

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.joda.time.DateTime
import org.joda.time.Duration
import java.util.*

open class Schedule: RealmObject() {
    @PrimaryKey
    var id = Date().time
    var start: Date = Date()
    var time: Int = 0
    var sprinkler1Status: Boolean = false
    var sprinkler2Status: Boolean = false
    var sprinkler1Done: Boolean = false
    var sprinkler2Done: Boolean = false
    var errorMessage: String? = null

    val isDone: Boolean
            get() {
                return sprinkler1Done && sprinkler2Done
            }

    val timeRemainingSprinkler1: Int
        get () {
            val now = DateTime()
            val duration = Duration(DateTime(start), now)
            val remaining = time - duration.standardSeconds
            return if (remaining < 0) 0 else remaining.toInt()
        }

    val timeRemainingSprinkler2: Int
        get () {
            if (sprinkler1Status) {
                return time
            }
            val now = DateTime()
            val duration = Duration(DateTime(start), now)
            val remaining = time * 2 - duration.standardSeconds
            return if (remaining < 0) 0 else remaining.toInt()

        }

}