 [ ![Download](https://api.bintray.com/packages/fangxiaole/maven/SmartLink/images/download.svg) ](https://bintray.com/fangxiaole/maven/SmartLink/_latestVersion)
# SmartLink
jitpack
===
## 1.配置  
Step 1.Add it in your root build.gradle at the end of repositories:  

        allprojects {
		        repositories {
			        ...
			        maven { url 'https://jitpack.io' }
		        }
	      }
 Step 2. Add the dependency: 
 
        dependencies {
	        compile 'com.github.LelePig:SmartLink:v1.3'
	     }
## 2.使用
        ElianNative elianNative=new ElianNative();
        boolean isLoad=elianNative.LoadLib();//加载动态库
        elianNative.InitSmartConnection(null,1,1);//初始化 固定填入参数为(null,1,1)
        elianNative.StartSmartConnection(ssid,pwd,"",mAuthMode);//开始发包（WiFi的SSID,WiFi密码，空字符串，加密方式）
        elianNative.StopSmartConnection();//停止发包
        
        加密方式      mAuthMode
        WPA/WPA2 PSK    9
        WPA2 PSK        7
        WPA PSK         4
        WPA/WPA2 EAP    8
        WPA2 EAP        6        
        WPA EAP         3
        其它            0    
jcenter
compile 'com.lele.smartlink:smartlinklibrary:0.0.1'
