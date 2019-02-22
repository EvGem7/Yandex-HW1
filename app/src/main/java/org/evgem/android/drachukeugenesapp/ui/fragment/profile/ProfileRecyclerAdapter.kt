package org.evgem.android.drachukeugenesapp.ui.fragment.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.util.ProfileConfig.CHAT
import org.evgem.android.drachukeugenesapp.util.ProfileConfig.CREDIT_CARD
import org.evgem.android.drachukeugenesapp.util.ProfileConfig.EMAIL
import org.evgem.android.drachukeugenesapp.util.ProfileConfig.GITHUB
import org.evgem.android.drachukeugenesapp.util.ProfileConfig.PHONE_NUMBER
import org.evgem.android.drachukeugenesapp.util.defaultSharedPreferences

class ProfileRecyclerAdapter : RecyclerView.Adapter<ProfileRecyclerViewHolder>() {
    override fun onCreateViewHolder(container: ViewGroup, type: Int): ProfileRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_profile, container, false)
        return ProfileRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int = ITEM_COUNT

    override fun onBindViewHolder(holder: ProfileRecyclerViewHolder, pos: Int) {
        val context = holder.itemView.context
        val sharedPreferences = context.defaultSharedPreferences
        val resources = context.resources

        val value: String
        val icon: Int
        when (pos) {
            PHONE_POS -> {
                val defaultValue = resources.getString(R.string.phone_number)
                value = sharedPreferences.getString(PHONE_NUMBER, defaultValue) ?: defaultValue
                icon = R.drawable.ic_phone
                holder.itemView.setOnClickListener { onPhoneClick(context, value) }
            }
            EMAIL_POS -> {
                val defaultValue = resources.getString(R.string.email)
                value = sharedPreferences.getString(EMAIL, defaultValue) ?: defaultValue
                icon = R.drawable.ic_email
                holder.itemView.setOnClickListener { onEmailClick(context, value) }
            }
            CARD_POS -> {
                val defaultValue = resources.getString(R.string.credit_card)
                value = sharedPreferences.getString(CREDIT_CARD, defaultValue) ?: defaultValue
                icon = R.drawable.ic_credit_card
                holder.itemView.setOnClickListener { onCardClick(context) }
            }
            CHAT_POS -> {
                val defaultValue = resources.getString(R.string.telegram)
                value = sharedPreferences.getString(CHAT, defaultValue) ?: defaultValue
                icon = R.drawable.ic_message
                holder.itemView.setOnClickListener { onHyperlinkClick(context, value) }
            }
            GITHUB_POS -> {
                val defaultValue = resources.getString(R.string.github_link)
                value = sharedPreferences.getString(GITHUB, defaultValue) ?: defaultValue
                icon = R.drawable.ic_github
                holder.itemView.setOnClickListener { onHyperlinkClick(context, value) }
            }
            else -> throw IllegalArgumentException("wrong view holder position")
        }
        holder.bind(icon, value)
    }

    private fun onPhoneClick(context: Context, number: String) {
        val callUri = Uri.parse("tel:$number")
        val intent = Intent(Intent.ACTION_DIAL, callUri)
        context.startActivity(intent)
    }

    private fun onEmailClick(context: Context, email: String) {
        val emailUri = Uri.parse("mailto:$email")
        val intent = Intent(Intent.ACTION_VIEW, emailUri)
        context.startActivity(intent)
    }

    private fun onCardClick(context: Context) {
        Toast.makeText(context, "Я не знаю зачем вам моя карта :(", Toast.LENGTH_SHORT).show()
    }

    private fun onHyperlinkClick(context: Context, link: String) {
        val linkUri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, linkUri)
        context.startActivity(intent)
    }

    companion object {
        private const val ITEM_COUNT = 5

        private const val PHONE_POS = 0
        private const val EMAIL_POS = 1
        private const val CARD_POS = 2
        private const val CHAT_POS = 3
        private const val GITHUB_POS = 4
    }
}