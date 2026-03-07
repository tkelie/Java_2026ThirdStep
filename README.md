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
│   │   │   ├── step1_hello/          # Spring Boot 起動・Hello World
│   │   │   ├── step2_rest_get/       # GET API 実装
│   │   │   ├── step3_rest_post/      # POST API・リクエストボディ
│   │   │   ├── step4_service_layer/  # Service 層・責務分離
│   │   │   ├── step5_log_api/        # ログ解析 API（SecondStep 移植）
│   │   │   └── step6_error_handling/ # エラーハンドリング
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           ├── step2_rest_get/
│           ├── step3_rest_post/
│           ├── step4_service_layer/
│           ├── step5_log_api/
│           └── step6_error_handling/
├── samplefile/
├── pom.xml
└── README.md
```

---

# 🧩 練習ロードマップ（5〜7日）

| STEP | テーマ | 実装内容 | 学習キーワード |
|---|---|---|---|
| 【済】STEP 1 | Spring Boot 起動 | Hello World API を起動する | `@RestController` `@GetMapping` Spring Initializr |
| STEP 2 | GET API 実装 | ログ一覧を返す `GET /api/logs` | `ResponseEntity` `@PathVariable` `@RequestParam` |
| STEP 3 | POST API 実装 | ログ登録 `POST /api/logs` | `@RequestBody` DTO パターン HTTP ステータス |
| STEP 4 | Service 層分離 | `@Service` に業務ロジックを移す | `@Service` `@Autowired` DI（依存性注入）|
| STEP 5 | ログ解析 API | SecondStep の CLI ロジックを API 化 | Service 再利用 `groupingBy` Stream API |
| STEP 6 | エラーハンドリング | 不正リクエストにエラー応答 | `@ExceptionHandler` `@ControllerAdvice` |

---

# 📝 各 STEP の詳細

---

## STEP 1：Spring Boot 起動・Hello World

### 🌱 A-1：プロジェクトセットアップ：【2026/03/07実施】

- Spring Initializr で pom.xml を生成（依存：Spring Web）
- `@SpringBootApplication` でアプリ起動を確認
- `GET /hello` → `"Hello, Spring Boot!"` を返す API を実装

**学習内容**

- Spring Boot プロジェクト構造の理解
- `@RestController` / `@GetMapping` の基本
- `mvn spring-boot:run` での起動方法

---

## STEP 2：GET API 実装

### 🌱 B-1：ログ一覧取得 API

- `GET /api/logs` → 全ログを JSON で返す
- `GET /api/logs/{userId}` → 特定ユーザーのログを返す
- MockMvc でレスポンスを検証するテストを書く

**学習内容**

- `ResponseEntity<T>` の使い方
- `@PathVariable` / `@RequestParam`
- `@WebMvcTest` + MockMvc の基本

---

## STEP 3：POST API 実装

### 🌱 C-1：ログ登録 API

- `POST /api/logs` → リクエストボディでログを受け取り登録
- `@RequestBody` でリクエスト JSON を DTO にマッピング
- 登録成功時に `201 Created` を返す

**学習内容**

- `@RequestBody` / DTO パターン
- HTTP ステータスコードの使い分け（200 / 201 / 400 など）
- POST エンドポイントのテスト（MockMvc + JSON）

---

## STEP 4：Service 層・責務分離

### 🌱 D-1：Controller と Service を分離する

- Controller：リクエスト受付・レスポンス生成のみ
- Service：業務ロジック（ログ集計・フィルタ）を担当
- `@Autowired` で DI（依存性注入）を体験

**クラス構成**

```
LogController    ← HTTP 層
LogService       ← 業務ロジック層
LogRepository    ← データ保持（インメモリ）
```

**学習内容**

- `@Service` / `@Repository` アノテーション
- DI（依存性注入）の仕組みと利点
- SecondStep の `LogReader` / `LogCounter` の移植

---

## STEP 5：ログ解析 API（SecondStep 移植）

### 🌱 E-1：CLI ロジックを REST API として公開

- `GET /api/logs/summary` → userId 別 LOGIN 回数
- `GET /api/logs/duplicate` → 複数回 LOGIN ユーザー
- SecondStep の Stream API ロジックを Service 層に移植

**実装クラス**

```
LogController
LogAnalysisService   ← SecondStep の LogCounter / UserFinder を統合
LogRepository
```

**学習内容**

- 既存ロジックの再利用と移植
- `groupingBy` / `counting` を使った集計 API
- Stream API と Spring Boot の組み合わせ

---

## STEP 6：エラーハンドリング

### 🌱 F-1：不正リクエストへの対応

- 存在しない `userId` を指定した場合 `404 Not Found` を返す
- カスタム例外クラス `UserNotFoundException` を作成
- `@ControllerAdvice` でグローバルにエラーを処理

**学習内容**

- `@ExceptionHandler` / `@ControllerAdvice`
- カスタム例外クラスの設計
- エラーレスポンス用 DTO（`ErrorResponse`）

---

# 🌐 実装する API 一覧

| メソッド | エンドポイント | ステータス | 説明 |
|---|---|---|---|
| GET | `/hello` | 200 OK | Hello World（STEP 1）|
| GET | `/api/logs` | 200 OK | 全ログ取得（STEP 2）|
| GET | `/api/logs/{userId}` | 200 / 404 | ユーザー別ログ取得（STEP 2）|
| POST | `/api/logs` | 201 Created | ログ登録（STEP 3）|
| GET | `/api/logs/summary` | 200 OK | LOGIN 回数集計（STEP 5）|
| GET | `/api/logs/duplicate` | 200 OK | 複数回 LOGIN ユーザー（STEP 5）|

---

# 🧠 使用する技術・API

| 機能・ツール | 内容 |
|---|---|
| Spring Boot 4.0.3 | アプリケーションフレームワーク |
| `@RestController` | REST API エンドポイントの定義 |
| `@Service` | 業務ロジック層のアノテーション |
| `@Repository` | データアクセス層のアノテーション |
| MockMvc | Spring Boot Test による API テスト |
| `@ExceptionHandler` | 例外ハンドリング |
| Stream API | `groupingBy` / `filter` による集計処理（SecondStep 継続）|
| `record` | DTO・ログデータ定義 |
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
