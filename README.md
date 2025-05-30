
---

## 專案介紹：ChipTestMetrics

### 專案目的與功能
`ChipTestMetrics` 是一個專注於晶圓測試數據分析的專案，旨在幫助管理和分析晶圓測試過程中的關鍵指標（Metrics），例如電壓（Voltage）和電流（Current）。這個專案提供了一個簡單而有效的平台，允許使用者儲存、查詢和分析晶圓測試數據，以便優化晶圓製造流程和品質控制。

- **核心功能**：
    1. **數據儲存**：透過 REST API 或初始化機制，將晶圓測試數據（如晶片 ID、電壓、電流、測試日期）儲存到資料庫中。
    2. **數據查詢**：提供 API 端點，允許使用者獲取所有測試數據或根據特定 ID 查詢單筆數據。
    3. **數據初始化**：支援從 JSON 檔案自動載入測試數據，方便開發和測試階段快速填充資料庫。
    4. **程式碼質量檢查**：整合 PMD 靜態分析工具，確保程式碼符合最佳實踐和規範。

### 特點與優點
- **特點**：
    1. **模組化設計**：採用 Spring Boot 框架，遵循 MVC（Model-View-Controller）架構，將模型（`TestMetric`）、服務層（`TestMetricService`）和控制器（`TestMetricController`）分離，易於擴展和維護。
    2. **輕量級資料庫**：使用 H2 記憶體資料庫進行開發和測試，無需額外安裝資料庫軟體，啟動即用。
    3. **自動化數據載入**：透過 `DataInitializer` 類別，支援從 JSON 檔案自動初始化測試數據，節省手動輸入時間。
    4. **程式碼質量保證**：整合 PMD 插件進行靜態程式碼分析，幫助開發者發現潛在問題並提升程式碼品質。
    5. **測試覆蓋率高**：包含服務層和控制器層的單元測試（`TestMetricServiceTest` 和 `TestMetricControllerTest`），確保功能正確性。

- **優點**：
    1. **易於使用**：提供簡單的 REST API，使用者可以透過工具如 Postman 或瀏覽器輕鬆與系統互動。
    2. **高效開發**：Spring Boot 提供內建伺服器和自動配置功能，減少開發者的設定負擔。
    3. **靈活性**：支援資料庫切換（從 H2 到 MySQL 或其他資料庫只需修改設定），適應不同環境需求。
    4. **可擴展性**：程式碼結構清晰，方便新增功能，例如數據分析、圖表視覺化或更多測試指標。
    5. **健壯性**：透過日誌框架（SLF4J）記錄運行資訊和錯誤，提升系統的可追蹤性和除錯能力。

### 目標使用者族群
- **半導體產業工程師**：負責晶圓測試和品質控制的工程師，可以使用此系統記錄和分析測試數據，快速發現異常值或趨勢。
- **數據分析人員**：需要處理大量晶圓測試數據的人員，可以利用 API 獲取數據並進行進一步分析。
- **軟體開發者**：希望開發或擴展晶圓測試相關應用程式的開發者，可以以此專案為基礎，新增功能或整合其他系統。
- **測試與品管團隊**：需要一個簡單工具來管理和查詢測試數據的團隊，特別是中小型企業或實驗室環境。

---

## 程式碼運用原理與邏輯分析

以下是對 `ChipTestMetrics` 專案中核心程式碼的運用原理和邏輯的詳細分析，幫助您理解系統如何運作。

### 1. 專案架構與技術棧
- **框架**：Spring Boot 3.5.0，基於 Java 17，提供快速開發和內建伺服器（Tomcat）。
- **資料庫**：H2 記憶體資料庫，透過 Spring Data JPA 進行 ORM（物件關聯映射），簡化資料庫操作。
- **建置工具**：Maven，用於依賴管理和專案建置。
- **程式碼分析**：PMD 插件，檢查程式碼質量和潛在問題。
- **測試框架**：JUnit 5 和 Mockito，用於單元測試和模擬依賴。

專案採用分層架構：
- **模型層（Model）**：定義數據結構，例如 `TestMetric` 類別。
- **存儲庫層（Repository）**：處理資料庫操作，例如 `TestMetricRepository`。
- **服務層（Service）**：處理業務邏輯，例如 `TestMetricService`。
- **控制器層（Controller）**：處理 HTTP 請求和回應，例如 `TestMetricController`。
- **配置層（Config）**：處理初始化和其他配置，例如 `DataInitializer`。

### 2. 核心程式碼邏輯分析

#### 2.1 模型類別：`TestMetric.java`
- **路徑**：`src/main/java/org/example/chiptestmetrics/model/TestMetric.java`
- **功能**：定義晶圓測試數據的結構，包含 `id`（唯一識別碼）、`chipId`（晶片 ID）、`voltage`（電壓）、`current`（電流）和 `testDate`（測試日期）。
- **邏輯**：使用 JPA 註解（`@Entity`）將類別映射到資料庫表，使用 Lombok 的 `@Data` 自動生成 getter、setter 和 toString 方法，減少樣板程式碼。
- **原理**：Spring Data JPA 會根據這個類別自動在 H2 資料庫中創建對應的表（`TEST_METRIC`），並將物件操作轉換為 SQL 查詢。

#### 2.2 存儲庫介面：`TestMetricRepository.java`
- **路徑**：`src/main/java/org/example/chiptestmetrics/repository/TestMetricRepository.java`
- **功能**：提供資料庫操作的介面，繼承 `JpaRepository`，自動獲得 CRUD（創建、讀取、更新、刪除）方法。
- **邏輯**：Spring Data JPA 會動態生成實現類別，處理資料庫操作，例如 `save()`、`findAll()` 和 `findById()`。
- **原理**：透過 JPA 的 ORM 機制，開發者無需手寫 SQL，僅需調用方法即可完成資料庫操作。

#### 2.3 服務層：`TestMetricService.java`
- **路徑**：`src/main/java/org/example/chiptestmetrics/service/TestMetricService.java`
- **功能**：處理業務邏輯，作為控制器和存儲庫之間的中介層，提供儲存數據、獲取所有數據和根據 ID 獲取單筆數據的方法。
- **邏輯**：
    1. `saveMetric()`：接收 `TestMetric` 物件，調用 `repository.save()` 將其儲存到資料庫。
    2. `getAllMetrics()`：調用 `repository.findAll()` 返回所有數據。
    3. `getMetricById()`：調用 `repository.findById()` 根據 ID 查詢數據，若無結果則返回 `null`。
- **原理**：服務層隔離了業務邏輯和資料庫操作，方便未來擴展（例如新增數據驗證或分析邏輯），也便於單元測試（透過 Mockito 模擬存儲庫）。

#### 2.4 控制器層：`TestMetricController.java`
- **路徑**：`src/main/java/org/example/chiptestmetrics/controller/TestMetricController.java`
- **功能**：處理 HTTP 請求，提供 REST API 端點，允許使用者與系統互動。
- **邏輯**：
    1. `POST /api/metrics`：接收 JSON 格式的測試數據，調用 `service.saveMetric()` 儲存。
    2. `GET /api/metrics`：調用 `service.getAllMetrics()` 返回所有數據。
    3. `GET /api/metrics/{id}`：根據 ID 調用 `service.getMetricById()` 返回單筆數據。
- **原理**：Spring MVC 將 HTTP 請求映射到控制器方法，自動處理 JSON 序列化和反序列化，透過 `@RestController` 標記返回 JSON 格式的回應。

#### 2.5 數據初始化：`DataInitializer.java`
- **路徑**：`src/main/java/org/example/chiptestmetrics/config/DataInitializer.java`
- **功能**：在應用程式啟動時，從 `test-data.json` 檔案讀取測試數據並載入資料庫。
- **邏輯**：
    1. 使用 `CommandLineRunner` 在應用程式啟動後執行初始化邏輯。
    2. 檢查資料庫是否已有數據，若有則跳過初始化。
    3. 使用 Jackson 的 `ObjectMapper` 讀取 JSON 檔案，轉換為 `TestMetric` 陣列。
    4. 逐筆調用 `repository.save()` 儲存數據。
    5. 使用 SLF4J 記錄初始化結果或錯誤訊息。
- **原理**：Spring Boot 的 `CommandLineRunner` 介面允許在應用程式上下文初始化後執行特定任務，適合用於數據初始化。Jackson 庫負責 JSON 解析，SLF4J 提供日誌記錄功能。

#### 2.6 測試案例：`TestMetricServiceTest.java` 和 `TestMetricControllerTest.java`
- **路徑**：`src/test/java/org/example/chiptestmetrics/service/` 和 `controller/`
- **功能**：驗證服務層和控制器層的功能是否正確。
- **邏輯**：
    - 服務層測試：使用 Mockito 模擬 `TestMetricRepository`，測試儲存和查詢邏輯。
    - 控制器層測試：使用 `WebMvcTest` 和 MockMvc 模擬 HTTP 請求，測試 API 端點的回應。
- **原理**：單元測試隔離了依賴（透過模擬），確保每個組件獨立正確運作，MockMvc 模擬 HTTP 環境，無需啟動完整伺服器即可測試 API。

#### 2.7 程式碼質量檢查：PMD 插件
- **設定**：在 `pom.xml` 中配置 `maven-pmd-plugin`。
- **功能**：靜態分析程式碼，檢查潛在問題（如使用 `System.out.println` 或未使用的變數）。
- **邏輯**：Maven 建置時執行 PMD 檢查，若有違規則輸出報告，根據設定決定是否失敗建置。
- **原理**：PMD 基於預定義規則集掃描程式碼，幫助開發者遵循最佳實踐，提升程式碼可讀性和可維護性。

### 3. 整體運作流程
1. **應用程式啟動**：
    - Spring Boot 啟動 `ChipTestMetricsApplication`，載入應用程式上下文。
    - `DataInitializer` 執行，讀取 `test-data.json` 並初始化資料庫（若資料庫為空）。
2. **數據操作**：
    - 使用者透過 API 端點（如 `POST /api/metrics`）發送請求。
    - 控制器接收請求，調用服務層處理業務邏輯。
    - 服務層調用存儲庫層，與資料庫互動。
    - 結果透過控制器以 JSON 格式返回給使用者。
3. **測試與檢查**：
    - 開發者執行 `mvn test` 運行單元測試，確保功能正確。
    - 執行 `mvn pmd:check` 檢查程式碼質量，根據報告修正問題。

---

## 總結

`ChipTestMetrics` 是一個專為晶圓測試數據管理設計的專案，適合半導體產業相關人員和開發者使用。其特點包括模組化設計、輕量級資料庫、自動化數據載入和高測試覆蓋率，優點則在於易用性、高效開發和可擴展性。程式碼採用 Spring Boot 框架，遵循分層架構，透過 JPA、REST API 和日誌框架實現數據管理與互動，並整合 PMD 和單元測試確保品質。

如果您對專案有進一步需求（例如新增數據分析功能或視覺化圖表），或者對某些程式碼邏輯有更深入的疑問，請隨時告訴我，我會進一步協助您！😊

---
