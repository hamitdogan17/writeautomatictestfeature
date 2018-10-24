Feature: Kullanıcı Login İşlemi

	Scenario: Başarılı ve Başarısız Login Testi

		GIVEN Arıkovanı Ana Sayfasına girilir. [https://arikovani.com/]
		AND Açılan sayfada “E-posta ile Giriş Yap” tabı seçilir.
		WHEN E-posta Adresi ve Şifre hatalı girilerek “Giriş Yap” butonuna tıklanılır.
		THEN Ekranda aşağıdaki hata mesajının çıktığı görülür. [”Geçersiz kullanıcı adı ve/veya şifre”]
		WHEN E-posta Adresi ve Şifre doğru girilerek “Giriş Yap” butonuna tıklanılır.
		THEN Hata mesajı gösterilmeden Arıkovanı Ana Sayfası yönlendirildiği görülür. “E-Posta” “Sifre” [caglar23@gmail.com,  Test1234]
		AND Web sayfasından çıkılır.

