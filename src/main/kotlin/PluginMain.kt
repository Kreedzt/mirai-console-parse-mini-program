package org.example.mirai.plugin

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import net.mamoe.mirai.console.command.CommandSender.Companion.toCommandSender
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.info
import kotlin.math.log

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
            this.toCommandSender().sendMessage("Received")
        }

        this.globalEventChannel().subscribeAlways<FriendMessageEvent>{ event ->
            logger.info("This is friendMessage: ${event.senderName}")
            event.toCommandSender().sendMessage("朋友消息")
            // 小程序
            if (event.message.filterIsInstance<LightApp>().isNotEmpty()) {
                logger.info("小程序消息, 消息内容: ${event.message.content}")
                val jsonObj = Json.parseToJsonElement(event.message.content)
                val desc = jsonObj.jsonObject.get("desc")
                val meta = jsonObj.jsonObject.get("meta")
                logger.info("desc: $desc")
                logger.info("meta: $meta")
                val metaUrl = meta?.jsonObject?.get("detail_1")?.jsonObject?.get("qqdocurl")
                event.toCommandSender().sendMessage("已解析小程序的metaUrl:$metaUrl")
                logger.info("metaUrl: $metaUrl")
            }
            // 文本消息
            if (event.message.filterIsInstance<PlainText>().isNotEmpty()) {
                logger.info("文本消息, 消息内容: ${event.message.content}")
            }
        }

        this.globalEventChannel().subscribeAlways<GroupMessageEvent> { event ->
            logger.info("This is groupMessage: ${event.senderName}")
            event.toCommandSender().sendMessage("群组消息")
        }
    }
}