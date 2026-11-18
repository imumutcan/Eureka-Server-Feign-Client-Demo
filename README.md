# Tepsiburada - Eureka Server & OpenFeign Client Demo

Bu proje, mikroservis mimarisinde **Spring Cloud Netflix Eureka (Service Discovery)** ve **Spring Cloud OpenFeign (Declarative REST Client)** kullanım mantığını göstermek amacıyla geliştirilmiş örnek bir uygulamadır.

---

## Mimari ve Servisler

Proje 3 ana bileşenden oluşmaktadır:

| Servis Adı | Klasör | Port | Açıklama |
| :--- | :--- | :---: | :--- |
| **`eureka-server`** | `eureka-server/` | `8761` | Mikroservislerin kayıt olduğu ve birbirini keşfettiği **Service Registry** sunucusu. |
| **`product-service`** | `product/` | `8082` | Ürün bilgilerini tutar. Bir ürün sorgulandığında **OpenFeign** aracılığıyla `campaign-service`'e istek atarak aktif kampanya/indirim oranını çeker ve indirimli fiyatı hesaplar. |
| **`campaign-service`** | `campaign/` | `8083` | Ürünlere ait kampanya ve indirim oranlarını (`saleRate`) yönetir. |

### İletişim Akışı
1. `product-service` ve `campaign-service` ayağa kalktığında kendilerini `eureka-server` (`http://localhost:8761`) üzerine kaydeder.
2. Kullanıcı `product-service` üzerinden bir ürünü sorguladığında (`GET /api/v1/product/{productId}`), `product-service` sabit bir IP/port kullanmak yerine `@FeignClient(name = "campaign-service")` ile Eureka üzerinden `campaign-service` adresini çözümler.
3. Ürüne tanımlı aktif bir kampanya varsa indirimli fiyat (`salePrice`) hesaplanarak döner; kampanya yoksa (`404 Not Found`) ürün indirimsiz fiyatıyla döner.

---

## Teknolojiler

- **Java 17**
- **Spring Boot 4.1.1** & **Spring Cloud 2025.1.3**
- **Spring Cloud Netflix Eureka Server & Client**
- **Spring Cloud OpenFeign**
- **Spring Data JPA & H2 In-Memory Database**
- **Maven**

---

## Çalıştırma Sırası

Servislerin birbirini sorunsuz bulabilmesi için aşağıdaki sırayla başlatılması önerilir:

### 1. Eureka Server'ı Başlatın
```bash
cd eureka-server
./mvnw spring-boot:run
```
- **Eureka Dashboard:** http://localhost:8761

### 2. Campaign Service'i Başlatın
```bash
cd campaign
./mvnw spring-boot:run
```
- **H2 Console:** http://localhost:8083/h2-console (`JDBC URL: jdbc:h2:mem:testdb`, `User: sa`)

### 3. Product Service'i Başlatın
```bash
cd product
./mvnw spring-boot:run
```
- Başlangıçta (`CommandLineRunner` ile) veritabanına otomatik olarak 3 örnek ürün eklenir ve UUID'leri konsola yazdırılır.
- **H2 Console:** http://localhost:8082/h2-console (`JDBC URL: jdbc:h2:mem:testdb`, `User: sa`)

---

## API Kullanımı ve Test Senaryosu

### 1. Tüm Ürünleri Listeleme (`product-service`)
Başlangıçta oluşturulan ürünlerin `productId` değerlerini öğrenmek için:

```http
GET http://localhost:8082/api/v1/product
```

**Örnek Yanıt:**
```json
[
  {
    "productId": "550e8400-e29b-41d4-a716-446655440000",
    "productName": "Fırın Tepsi",
    "price": 1250.0,
    "salePrice": 1250.0,
    "saleRate": 0.0
  }
]
```

### 2. Bir Ürüne Kampanya Tanımlama (`campaign-service`)
Yukarıdan aldığınız `productId` ile ürüne `%20` indirim tanımlayın:

```http
POST http://localhost:8083/api/v1/campaign
Content-Type: application/json

{
  "productId": "550e8400-e29b-41d4-a716-446655440000",
  "saleRate": 20.0
}
```

### 3. Ürünü Tekrar Sorgulama (`product-service` -> Feign -> `campaign-service`)
```http
GET http://localhost:8082/api/v1/product/550e8400-e29b-41d4-a716-446655440000
```

**Örnek Yanıt:**
```json
{
  "productId": "550e8400-e29b-41d4-a716-446655440000",
  "productName": "Fırın Tepsi",
  "price": 1250.0,
  "salePrice": 1000.0,
  "saleRate": 20.0
}
```
