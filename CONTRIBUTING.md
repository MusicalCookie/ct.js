# Development Workspace Setup
Setup is a little bit more involved than just a normal Forge dev workspace setup but isn't too hard. Follow these steps and you should be ready to submit pull requests.

### 0. Set up JDK 8 and set your JAVA_HOME path variable (use google)
### 1. Clone the repository to your computer<br>
![clone repo](http://i66.tinypic.com/9jdlp5.png)
### 2. Open PowerShell/command prompt
  - shift + right click > open PowerShell
### 3. Run gradle setup commands
  - cmd         `gradlew setupDecompWorkspace`
  - PowerShell  `./gradlew setupDecompWorkspace`
  - cmd         `gradlew idea`
  - PowerShell  `./gradlew idea`
### 4. Set up intellij idea
  - right click project > open module settings<br>
![open module settings](https://i.imgur.com/F7clio5.png)
  - project > project language level > SDK default (8)<br>
![set language level](http://i66.tinypic.com/2rormrn.png)
  - file > settings<br>
![open idea settings](http://i65.tinypic.com/35bco0h.png)
  - plugins > brows all repositories > search "lombok" > install<br>
![install lombok](http://i67.tinypic.com/t8sv2p.png)
  - still in settings > Build, Execution, Deployment > Annotation Processors > Enable annotation processing
![enable annotation processing](http://i66.tinypic.com/676slz.png)
### 5. Debug client setup
  - run > edit configuration<br>
![edit configuration](http://i65.tinypic.com/t6yq7b.png)
  - Use classpath for module > select the main ct.js (it might be named differently depending on your fork)<br>
![classpath](http://i67.tinypic.com/15z34fd.png)

Everything should be set up and ready to open pull requests
