# Java Practice – Third Step (2026)

このリポジトリは  
**Java エンジニア復帰の第3段階の学習記録**です。

[SecondStep](https://github.com/tkelie/Java_2026SecondStep) で身につけた  
JUnit・例外設計・責務分離・CLI ツール化をベースに、

**Spring Boot を使った REST API の設計・実装・テスト**

を学習します。  
SecondStep の CLI ログ解析ツールを Web API として再構築しながら、  
実務で頻出の Spring Boot の基礎を体系的に習得することが目標です。

---

# 🔗 学習の流れ

| リポジトリ | テーマ |
|---|---|
| [FirstStep](https://github.com/tkelie/Java_2026FirstStep) | Java 8+ 構文（Stream / Optional / java.time）|
| [SecondStep](https://github.com/tkelie/Java_2026SecondStep) | JUnit・例外設計・責務分離・CLI ツール化 |
| **ThirdStep（本リポジトリ）** | **Spring Boot REST API 入門** |

---

# 🎯 学習の目的

- Spring Boot プロジェクトのセットアップを理解する
- REST API（GET / POST）の設計と実装ができる
- `@RestController` / `@Service` / `@Repository` の役割分担を習得する
- Spring Boot Test（MockMvc）を使った API テストを書ける
- SecondStep の CLI ロジックを Service 層へ移植・再利用できる
- エラーハンドリング（`@ExceptionHandler`）を実装できる

---

# 📂 ディレクトリ構成

```
.
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/Java_2026ThirdStep/
│   │   │       ├── Java2026ThirdStepApplication.java
│   │   │       ├── HelloController.java
│   │   │       ├── Log.java
│   │   │       ├── LogController.java
│   │   │       ├── LogService.java
│   │   │       ├── LogRepository.java
│   │   │       ├── UserNotFoundException.java
│   │   │       ├── ErrorResponse.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/Java_2026ThirdStep/
│               ├── Java2026ThirdStepApplicationTests.java
│               └── LogControllerTest.java
├── pom.xml
└── README.md
```

---

# 🧩 練習ロードマップ

| STEP | テーマ | 実施日 | 状態 |
|---|---|---|---|
| STEP 1 | Spring Boot 起動・Hello World | 2026/03/07 | ✅ |
| STEP 2 | GET API 実装 | 2026/03/08 | ✅ |
| STEP 3 | POST API 実装 | 2026/03/08 | ✅ |
| STEP 4 | Service 層分離 | 2026/03/08 | ✅ |
| STEP 5 | ログ解析 API | 2026/03/08 | ✅ |
| STEP 6 | エラーハンドリング | 2026/03/08 | ✅ |

---

# 📝 各 STEP の詳細

---

## STEP 1：Spring Boot 起動・Hello World【2026/03/07実施】

### 🌱 A-1：プロジェクトセットアップ

- Spring Initializr で pom.xml を生成（依存：Spring Web）
- `@SpringBootApplication` でアプリ起動を確認
- `GET /hello` → `"Hello, Spring Boot!"` を返す API を実装

**学習内容**

- Spring Boot プロジェクト構造の理解
- `@RestController` / `@GetMapping` の基本
- `mvn spring-boot:run` での起動方法

---

## STEP 2：GET API 実装【2026/03/08実施】

### 🌱 B-1：ログ一覧取得 API

- `GET /api/logs` → 全ログを JSON で返す
- `GET /api/logs/{userId}` → 特定ユーザーのログを返す
- MockMvc でレスポンスを検証するテストを書く

**学習内容**

- `@PathVariable` で URL の一部を変数として受け取る
- `List<Log>` を返すだけで Spring Boot が自動で JSON に変換してくれる
- `@SpringBootTest` + `@AutoConfigureMockMvc` + MockMvc による API テスト

**MockMvc テストのポイント**

```java
@SpringBootTest
@AutoConfigureMockMvc
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 全ログ取得_200が返る() throws Exception {
        mockMvc.perform(get("/api/logs"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].userId").value("alice"));
    }
}
```

> ※ Spring Boot 4.0.3 では `spring-boot-starter-webmvc-test` の追加と  
> `import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;` が必要

---

## STEP 3：POST API 実装【2026/03/08実施】

### 🌱 C-1：ログ登録 API

- `POST /api/logs` → リクエストボディでログを受け取り登録
- `@RequestBody` でリクエスト JSON を `Log` オブジェクトに自動変換
- 登録成功時に `201 Created` を返す

**学習内容**

- `@RequestBody` / DTO パターン（`Log` record が DTO の役割）
- `ResponseEntity.status(201).body(log)` でステータスコードとボディをセットで返す
- HTTP ステータスコードの使い分け

| コード | 意味 | 使いどころ |
|---|---|---|
| 200 OK | 成功 | GET で取得成功 |
| 201 Created | 作成成功 | POST で登録成功 |
| 400 Bad Request | リクエストが不正 | JSON の形式が間違っている |
| 404 Not Found | 見つからない | 存在しない userId を指定 |

---

## STEP 4：Service 層・責務分離【2026/03/08実施】

### 🌱 D-1：Controller と Service を分離する

- Controller：リクエスト受付・レスポンス生成のみ
- Service：業務ロジック（ログ集計・フィルタ）を担当
- コンストラクタインジェクションで DI（依存性注入）を実現

**クラス構成**

```
LogController    ← HTTP 層
LogService       ← 業務ロジック層
LogRepository    ← データ保持（インメモリ）
```

**学習内容**

- `@Service` / `@Repository` アノテーション
- DI（依存性注入）の仕組みと利点

**DI のポイント**

```java
// 自分で new しない。Spring が自動で渡してくれる（コンストラクタインジェクション）
@RestController
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) { // Spring が自動で渡す
        this.logService = logService;
    }
}
```

> ※ `@Autowired` を使ったフィールドインジェクションも同じ動作だが、  
> 現在は**コンストラクタインジェクションが推奨**されている

---

## STEP 5：ログ解析 API（SecondStep 移植）【2026/03/08実施】

### 🌱 E-1：CLI ロジックを REST API として公開

- `GET /api/logs/summary` → userId 別 LOGIN 回数
- `GET /api/logs/duplicate` → 複数回 LOGIN ユーザー
- SecondStep の Stream API ロジックを Service 層に移植

**SecondStep からの対応関係**

| SecondStep（CLI） | ThirdStep（API） |
|---|---|
| `java -jar ... --summary` | `GET /api/logs/summary` |
| `java -jar ... --duplicate` | `GET /api/logs/duplicate` |
| `LogCounter.java` | `LogService.summarize()` |
| `UserFinder.java` | `LogService.findDuplicates()` |

**学習内容**

- 既存ロジックの再利用と移植
- `groupingBy` / `counting` を使った集計 API
- Stream API と Spring Boot の組み合わせ

---

## STEP 6：エラーハンドリング【2026/03/08実施】

### 🌱 F-1：不正リクエストへの対応

- 存在しない `userId` を指定した場合 `404 Not Found` を返す
- カスタム例外クラス `UserNotFoundException` を作成
- `@RestControllerAdvice` でグローバルにエラーを処理

**学習内容**

- `@ExceptionHandler` / `@RestControllerAdvice`
- カスタム例外クラスの設計
- エラーレスポンス用 DTO（`ErrorResponse` record）

---

# 🌐 実装した API 一覧

| メソッド | エンドポイント | ステータス | 説明 |
|---|---|---|---|
| GET | `/hello` | 200 OK | Hello World（STEP 1）|
| GET | `/api/logs` | 200 OK | 全ログ取得（STEP 2）|
| GET | `/api/logs/{userId}` | 200 / 404 | ユーザー別ログ取得（STEP 2）|
| POST | `/api/logs` | 201 Created | ログ登録（STEP 3）|
| GET | `/api/logs/summary` | 200 OK | LOGIN 回数集計（STEP 5）|
| GET | `/api/logs/duplicate` | 200 OK | 複数回 LOGIN ユーザー（STEP 5）|

---

# 🧠 使用した技術・API

| 機能・ツール | 内容 |
|---|---|
| Spring Boot 4.0.3 | アプリケーションフレームワーク |
| `@RestController` | REST API エンドポイントの定義 |
| `@Service` | 業務ロジック層のアノテーション |
| `@Repository` | データアクセス層のアノテーション |
| `@RequestBody` | リクエスト JSON を Java オブジェクトに変換 |
| `ResponseEntity` | ステータスコードとボディをセットで返す |
| `MockMvc` | Spring Boot Test による API テスト |
| `@ExceptionHandler` | 例外ハンドリング |
| `@RestControllerAdvice` | グローバル例外ハンドリング |
| Stream API | `groupingBy` / `filter` による集計処理（SecondStep 継続）|
| `record` | DTO・ログデータ定義（`Log` / `ErrorResponse`）|
| Maven | ビルド・依存関係管理（pom.xml）|

---

# 💻 開発環境

- Java 25.0.2
- Spring Boot 4.0.3（Spring Initializr でセットアップ）
- Maven 3.9.12
- VSCode
- GitHub
- Windows 11

---

# 🚀 今後の予定（FourthStep 候補）

- データベース連携（Spring Data JPA + H2 / PostgreSQL）
- 認証・認可（Spring Security 入門）
- Docker でのコンテナ化
- Swagger / OpenAPI によるドキュメント生成