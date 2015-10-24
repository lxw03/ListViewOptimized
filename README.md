# ListViewOptimized

使用了图片加载库ImageLoader进行图片的加载，ListView的主要优化包括：

  1. BaseAdapter中的ViewHolder的优化
  2. 图片异步加载、缓存
  3. 快速滑动列表时暂停图片的加载，停止滑动时加载图片
