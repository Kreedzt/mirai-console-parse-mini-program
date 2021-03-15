package org.example.mirai.plugin

import net.mamoe.mirai.console.command.CommandSender.Companion.toCommandSender
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.LightApp
import net.mamoe.mirai.message.data.MessageContent
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.info

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "org.example.mirai-parse-miniprogram-plugin",
        name = "ParseMiniProgramPlugin",
        version = "0.1.0"
    ) {
        author("Kreedzt")

        info("""
            解析QQ小程序链接
        """.trimIndent())

        // author 和 info 可以删除.
    }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }

        this.globalEventChannel().subscribeAlways<MessageEvent>{ event ->
            logger.info(event.toString())
            logger.info(event.message.toString())
            this.toCommandSender().sendMessage("Test Res")
        }

        this.globalEventChannel().subscribeAlways<FriendMessageEvent>{ event ->
            logger.info("This is friendMessage: ${event.senderName}")
            event.toCommandSender().sendMessage("朋友消息")
            logger.info("消息内容: ${event.message.content}")
            val la = LightApp(event.message.content)
            logger.info("la: ${la}")
        }

        this.globalEventChannel().subscribeAlways<GroupMessageEvent> { event ->
            logger.info("This is groupMessage: ${event.senderName}")
            event.toCommandSender().sendMessage("群组消息")
        }
    }
}