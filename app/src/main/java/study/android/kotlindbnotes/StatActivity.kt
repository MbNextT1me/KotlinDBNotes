package study.android.kotlindbnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.room.Room

class StatActivity : AppCompatActivity() {

    private lateinit var moneyTextView: TextView
    private lateinit var goodTextView: TextView
    private lateinit var englishTextView: TextView
    private lateinit var bestTextView: TextView
    private lateinit var longestTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)

        moneyTextView = findViewById(R.id.money)
        goodTextView = findViewById(R.id.good)
        englishTextView = findViewById(R.id.english)
        bestTextView = findViewById(R.id.best)
        longestTextView = findViewById(R.id.longest)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "results.db"
        ).build()

        db.resultsDao().getTotalCapitalization().observe(this) { totalCapitalization ->
            moneyTextView.text = totalCapitalization.toString()
        }

        db.resultsDao().getCompaniesAboveAverageCapitalization().observe(this) { count ->
            goodTextView.text = count.toString()
        }

        db.resultsDao().getEnglishCompaniesCount().observe(this) { count ->
            englishTextView.text = count.toString()
        }

        db.resultsDao().getBestCompany().observe(this) { company ->
            bestTextView.text = company.name
        }

        db.resultsDao().getLongestCompanyName().observe(this) { companyName ->
            longestTextView.text = companyName
        }
    }
}
