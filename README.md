# 悦唱K歌

对标唱享K歌 v1.0.21 的 Android K歌应用

## 功能特性

- ✅ 在线K歌（播放、录音、评分）
- ✅ 原唱/伴奏切换
- ✅ 智能打分系统
- ✅ 歌词同步显示
- ✅ 首页推荐（热门、新歌、免费专区）
- ✅ 分类浏览（10个分类）
- ✅ 歌曲搜索
- ✅ 用户系统（登录、VIP）
- ✅ 作品管理

## 技术栈

- Kotlin 1.9.22
- Hilt 依赖注入
- Room 数据库
- Retrofit + OkHttp
- ExoPlayer (Media3)
- Coil 图片加载
- MVVM 架构

## 编译

1. 克隆项目
```bash
git clone https://github.com/你的用户名/YueChangKTV.git
cd YueChangKTV
```

2. 编译 APK
```bash
./gradlew assembleDebug
```

## 下载 APK

[GitHub Actions](../../actions) 自动编译的 APK 可在 Artifacts 中下载。

## 项目结构

```
app/src/main/java/com/yuechang/ktv/
├── data/          # 数据层
├── domain/        # 领域层
├── service/       # 服务层
├── ui/            # UI层
├── di/            # 依赖注入
└── util/          # 工具类
```

## License

MIT License