package me.lovesasuna.bot.listener

import me.lovesasuna.bot.Main
import me.lovesasuna.bot.file.Config
import me.lovesasuna.bot.function.Sort
import me.lovesasuna.bot.util.interfaces.FunctionListener
import me.lovesasuna.bot.util.interfaces.MessageListener
import net.mamoe.mirai.event.subscribeFriendMessages
import net.mamoe.mirai.message.data.Image

class FriendMessageListener {
    private val listeners: MutableList<FunctionListener> = ArrayList()

    init {
        val listenersClass = arrayOf<Class<*>>(
                Sort::class.java
        )

        listenersClass.forEach { c -> listeners.add(c.getConstructor().newInstance() as FunctionListener) }
    }

    companion object : MessageListener {
        val listener = FriendMessageListener()
        override fun onMessage() {
            Main.bot.subscribeFriendMessages {
                always {
                    val senderID = sender.id
                    if (senderID != Config.data.admin) {
                        return@always
                    }
                    val message = message.contentToString()
                    val image = this.message[Image]
                    listener.listeners.forEach { listener -> listener.execute(this, message, image, null) }
                }
            }
        }
    }
}