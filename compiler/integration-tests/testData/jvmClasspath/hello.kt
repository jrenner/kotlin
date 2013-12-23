package hello

import sun.nio.cs.SingleByte
import sun.net.spi.nameservice.dns.DNSNameService
import javax.crypto.Cipher
import com.sun.java.browser.plugin2.DOM
import com.sun.crypto.provider.AESCipher

fun main(args : Array<String>) {
    val a = SingleByte()
    val c = DNSNameService()
    val e : Cipher? = null
    val f : AESCipher? = null
    val j : DOM? = null
    val result = "$a$c$e$f$j"
    println("OK")
}

