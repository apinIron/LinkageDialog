# LinkageDialog
仿淘宝地址城市选择对话框

由于项目中需要用到仿淘宝的地址城市选择对话框，所以使用了TabLayout + RecyclerView简单封装了一个城市选择对话框。
项目结构非常简单，也可以把代码加到自己项目中进行修改。

效果如下：

![](https://github.com/apinIron/LinkageDialog/blob/master/frame.gif)

## API：

setTitle(String title) //设置对话框的标题

setLinkageData(List<LinkageItem> data) //设置数据源(数据需实现LinkageItem接口)

setContentHeight(int contentHeight)  //设置内容高度（RecyclerView）

setTabIndicatorColor(int colorTabIndicator)  //设置tabLayout指示器颜色

setTabIndicatorHeight(int tabIndicatorHeight)  //设置tabLayout指示器高度

setTabTitleColor(int colorTabSelectTitleColor,int colorTabWaitSelectTitleColor)  //设置tabLayout标题选中和未选中的字体颜色

setOnLinkageSelectListener(IOnLinkageSelectListener listener)   //监听选择事件

setOnLinkageAdapterListener(IOnLinkageAdapterListener listener)   //如果需要自定义RecyclerView的item布局，需设置这个方法
