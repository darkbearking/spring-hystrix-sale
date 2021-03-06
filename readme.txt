Hystrix整合springboot之銷售端，調用服務提供者（member端）
或者這樣記憶：Hystrix要放在服務調用者工程中。
而且Hystrix要放在當前工程，因為業務假設的是服務提供者可能會掛掉
就是如“架構圖.png”所示的，假設會員工程的數據庫連接可能會異常導致服務不可用

具體的例子，可以參照MemberService類，這裡面有詳細的注釋
比如我們在之前單機版練習的Hystrix的那些配置在整合版中該如何配置
配置時具體參數值的填寫
再比如個性化回退配置與通用回退配置的相互間的區別和聯繫等

說點兒感想：
使用緩存的最終目的，就是減少調用service的次數。
因此，當你的controller的某個方法（即某個請求）需要多次調用某個service的方法的時候
只要你controller傳入service的入參不變，那麼Hystrix其實就只是調用了service的那個方法一次。

需要注意的就是：
如果你的入參對service的返回值沒有影響的
或者你需要service每次返回不同的結果，那麼你最好就不要使用緩存，因為那個情況緩存不支持。

合並請求：
相關內容在collapser包
意思就是，如果多個請求地址相同，但是參數不同，這時可以考慮將這些請求合併。
合併前，是n次獨立的請求，每次參數的值不同。
合並和，是1次請求，參數都被合併到一起。類似於sql查詢的where條件中的in條件。

整合feign
相關內容在feign包