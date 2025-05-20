# 框架介绍


### Buession CAS 框架是什么？
解决 Apereo CAS 的解决各依赖问题，以及对 Apereo CAS 二次封装，以及集成了阿里云、极验、腾讯云行为验证码。

1. Apereo CAS 自身会依赖很多第三方包，但是这些第三方包基本用不上，我们通过包装，把这些用不上的包给排除掉。
2. Apereo CAS 依赖的 javax 包我们统一替换为 jakarta