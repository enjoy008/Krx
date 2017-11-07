# Krx 简单异步操作库

## 参照RxJava编写，可在提供别人SDK里使用，以避免第三方的依赖。

- **使用简单** 仅提供两种方法创建KObersevable,即KObservable.from(T... values)和KObservable.create(KCall<T> call)
- **功能齐全** 转换有map,flatMap；线程切换有subscribeOn,observeOn；取消操作KDisposable.dispose()