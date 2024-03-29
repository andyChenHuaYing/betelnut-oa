package org.betelnut.modules.security.utils;

import org.betelnut.modules.utils.Encodes;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CryptosTest {
	@Test
	public void mac() {
		String input = "foo message";

		// key可为任意字符串
		// byte[] key = "a foo key".getBytes();
		byte[] key = Cryptos.generateHmacSha1Key();
		assertThat(key).hasSize(20);

		byte[] macResult = Cryptos.hmacSha1(input.getBytes(), key);
		System.out.println("hmac-sha1 key in hex      :" + Encodes.encodeHex(key));
		System.out.println("hmac-sha1 in hex result   :" + Encodes.encodeHex(macResult));

		assertThat(Cryptos.isMacValid(macResult, input.getBytes(), key)).isTrue();
	}

	@Test
	public void aes() {
		byte[] key = Cryptos.generateAesKey();
		assertThat(key).hasSize(16);
		String input = "foo message";

		byte[] encryptResult = Cryptos.aesEncrypt(input.getBytes(), key);
		String descryptResult = Cryptos.aesDecrypt(encryptResult, key);

		System.out.println("aes key in hex            :" + Encodes.encodeHex(key));
		System.out.println("aes encrypt in hex result :" + Encodes.encodeHex(encryptResult));
		assertThat(descryptResult).isEqualTo(input);
	}

	@Test
	public void aesWithIV() {
		byte[] key = Cryptos.generateAesKey();
		byte[] iv = Cryptos.generateIV();
		assertThat(key).hasSize(16);
		assertThat(iv).hasSize(16);
		String input = "foo message";

		byte[] encryptResult = Cryptos.aesEncrypt(input.getBytes(), key, iv);
		String descryptResult = Cryptos.aesDecrypt(encryptResult, key, iv);

		System.out.println("aes key in hex            :" + Encodes.encodeHex(key));
		System.out.println("iv in hex                 :" + Encodes.encodeHex(iv));
		System.out.println("aes encrypt in hex result :" + Encodes.encodeHex(encryptResult));
		assertThat(descryptResult).isEqualTo(input);
	}
}
