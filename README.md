# 研修: Java 実習用サービス加入者管理システム

このプロジェクトは、Java 実習用で使用するサービス加入者システムです。

本アプリケーションは Spring Framework 6(Spring Boot 3) を使用して作成されており、以下のパッケージを利用しています。

- Spring Web
  - Thymeleaf
- Spring Validation
- Spring Security
- Spring Data JPA
  - H2 Database 
  - PosgreSQL Driver
- Lombok

**本リポジトリを自分Githubアカウントにフォークして利用してください**

## 必要なソフトウエア

以下のソフトウエアを、公式サイトからダウンロードしてインストールしてください。

- Java Development Kit 17 以降
  - [Amazon Corrette](https://aws.amazon.com/jp/corretto)
  - [Eclipse Temurin](https://adoptium.net/temurin/releases/)
- IDEもしくはテキストエディタ
  - [Eclopse](https://www.eclipse.org/downloads/)
    - [Pleiades](https://willbrains.jp/) - Pleiades All in One - Eclipse日本語化プラグイン＋α
  - [Visual Studio Code](https://azure.microsoft.com/ja-jp/products/visual-studio-code)
- [Git for Windows](https://gitforwindows.org/)
- [Docker Desktop][https://www.docker.com/ja-jp/products/docker-desktop/]

### Linux(Ubuntu)

以下のコマンドを実行して、必要なソフトウエアをインストールしてください。

```sh
sudo apt install git openjdk-21-jdk-headless
wget -o vscode.deb https://code.visualstudio.com/sha/download?build=stable&os=linux-deb-x64
sudo apt install ./vscode.deb
```

## 開発方法

本プロジェクトは、 EclipseもしくはVisual Studio Code(以下、VSCode) で開発することを想定しています。
なお、開発ドキュメントは所定の場所に格納しています。事前に講師が説明をしますので、その場所のドキュメントを参照してください。

### Eclipse

本プロジェクトを適当な場所にクローンした後、Eclipseを起動して、インポート機能の「既存Mavenプロジェクト」で本プロジェクトをワークスペースにインポートします。

### VSCode

VSCode を起動し、フォークしたリポジトリを適当な場所にクローンしてください。その後、クローンしたフォルダを開きます。
初回クローンしたフォルダを VSCode で開くと、本プロジェクトが利用している拡張機能のインストールを求められますので、インストールしてください。


## 実行およびデバッグ方法

### Eclipse

Eclipseでインポートしたプロジェクトで、Alt+Shift+Xを同時押しした後、bキーを押します。

### VSCode

クローンしたフォルダを VSCode で開いた状態で、F5 キーを押します。


### コマンドライン

以下のコマンドを実行します。

(Windows)
```
mvnw spring-boot:run -pl webapp
```

(Linux)
```
./mvnw spring-boot:run -pl webapp
```

## ビルド方法

ターミナルから以下のコマンドを実行します。

```sh
./mvnw package
```

エラーがない場合は、`webapp/targe` フォルダには`webapp-0.0.1-SNAPSHOT.war` が、
`batch/target` フォルダには、`batch-0.0.1-SNAPSHOT.jar` が作成されます。

それぞれ、`java -jar <生成されたファイル>` とすることで実行できます。
