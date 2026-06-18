# ペットマッチングアプリ

## 概要
このアプリは、ユーザーが自分に合ったペットを効率よく見つけるためのWebアプリです。  
アンケートやクイズを通じてユーザーの性格やライフスタイルを分析し、最適なペットを提案します。  
従来の見た目重視の検索サイトと異なり、相性を重視したマッチングを行います。

## 主な機能
- ユーザー登録・ログイン・ログアウト
- アンケートによるユーザー分析
- クイズ形式によるペット適性診断
- ペット一覧表示・詳細表示
- 条件に基づいたペット検索
- おすすめペットの表示

## 技術スタック
- 言語: Java 25
- 画面: JSP
- サーバー処理: Servlet
- Webサーバー: Apache Tomcat（11.x）
- データベース: MySQL 8.0
- IDE: Eclipse

## 動作環境
- JDK 17 以上
- Apache Tomcat 11.x
- MySQL 8.0 以上

### 1. リポジトリのクローン
Eclipse の File > Import > Git からクローンするか、
コマンドラインで以下を実行します。
	
	git clone https://github.com/fsz-moriuchi/yariika.git

### 2. データベースの準備
MySQL で以下を実行し、データベースとテーブルを作成します。
SQLファイルは `/db/schema.sql` に用意しています。

	 mysql -u root -p < db/schema.sql
	 
### 3. 設定ファイルの編集
`src/main/resources/db.properties` を開き、
自分の環境に合わせてDB接続情報を編集します。

    db.url=jdbc:mysql://localhost:3306/taskboard
    db.user=（自分のMySQLユーザー名）
    db.password=（自分のMySQLパスワード）
   
### 4. Eclipseへのインポートと起動
1. Eclipse でプロジェクトをインポート
2. プロジェクトを右クリック > Run As > Run on Server
3. Tomcat を選択して起動

### 5. アクセス
ブラウザで以下にアクセスします。

    http://localhost:8080/yariika/WelcomeServlet

## 使い方
1. 初回はトップの「新規登録」からアカウントを作成
2. 作成したアカウントでログイン
3. 「新規タスク」からタスクを登録
4. 一覧画面でタスクの完了・編集・削除が可能

## ディレクトリ構成

    taskboard/
    ├── src/main/java/      … Servlet, DAO などのJavaコード
    ├── src/main/webapp/    … JSP, CSS, 画像
    ├── db/schema.sql       … テーブル作成用SQL
    └── README.md           … このファイル
    
## チームメンバー
- 森内: 画像挿入機能・お気に入り機能
- 船守: 認証機能・予約機能
- 松井: クイズ機能・メッセージ機能
- 林: アンケート機能・検索機能・マッチング機能・施設ペット管理