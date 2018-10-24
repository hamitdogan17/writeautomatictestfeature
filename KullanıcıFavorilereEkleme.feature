Feature: Kullanıcı Favorilere Ekleme

	Scenario: Kullanıcı Favorilere Ekleme

		GIVEN Arıkovanı Ana Sayfasına girilir. [https://arikovani.com/]
		AND Açılan sayfada “Yeni Hisler Kazandıran Akıllı Saat : Sense Watch” ürününün üzerine tıklanır.
		AND Açılan sayfada “Takip Et”  butonuna tıklanır.
		THEN Açılan popup içerisinde “Proje takip listenize eklendi.”  mesajının gösterildiği kontrol edilir.
		GIVEN Tamam butonuna tıklanılarak Popup kapatılır.
		THEN Web sayfasından çıkılır.

	Scenario: Kullanıcı Favorilere Ekleme

		GIVEN Arıkovanı Ana Sayfasına girilir. [https://arikovani.com/]
		AND Açılan sayfada “Yeni Hisler Kazandıran Akıllı Saat : Sense Watch” ürününün üzerine tıklanır.
		AND Açılan sayfada “Takip Et”  butonuna tıklanır.
		THEN Açılan popup içerisinde “Proje takip listenize eklendi.”  mesajının gösterildiği kontrol edilir.
		GIVEN Tamam butonuna tıklanılarak Popup kapatılır.
		THEN Web sayfasından çıkılır.

