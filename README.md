# 爬虫相关知识代码

> 读书笔记《自己动手写网络爬虫》，自己敲的代码。主要记录了网络爬虫的基本实现，网页去重的算法，网页指纹算法，文本信息挖掘

- ConsistentHash 一致hash算法
- HashAlgorithms hash算法大全
- MurmurHash MurMurHash算法，是非加密HASH算法，性能很高，碰撞率低
- IPSeeker 封装了腾讯的ip库，提供一些工具,读取QQwry.dat文件，以根据ip获得好友位置

- HITS HITS算法实现
- PageRank PageRank算法实现
- WebGraph Web图建模
- WebGraphMemory 内存Web图
- SimpleBloomFilter 布隆过滤器

- BDBFrontier 使用Berkeley DB 来做爬虫的前端url爬取列表存储
- Crawler 爬虫一只，采用了宽度优先的方式爬取网络，并且使用httpclien4.3来下载网页
- CrawlUrl 一个封装了爬虫的url地址的对象，可以使用其layer变量控制限制层次的爬取
- DownLoadFile 一个下载网页数据到本地的工具类

