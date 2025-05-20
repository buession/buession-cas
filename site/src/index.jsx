---
banner:
  name: 'Apereo CAS 扩展'
  desc: 'Apereo CAS 集成扩展。'
  btns: 
    - { name: '开 始', href: '/docs/quickstart.html', primary: true }
    - { name: 'Github >', href: 'https://github.com/buession/buession-cas' }
  caption: '当前版本: v3.0.1'
features: 
  - { name: '优雅', desc: '经过精雕细琢，我们带给大家一个精心设计的、标准的、高内聚低耦合的通用类库' }
  - { name: '灵活', desc: '非重复造车轮，我们是整合市面上开源的类库，以标准的接口暴露给上层用户，用户可替换或自行封装同类组件。在此基础上，封装了大量的常用的类库。' }
  - { name: '简洁', desc: '减少学习成本' }
  - { name: '开源', desc: '作为开源项目，我们不断提高代码质量和完善文档' }

footer:
  copyright:
    name: 'Buession Team'
    href: 'https://www.buession.com/'
  links:
    前端开源库:
      - { name: 'Buession Prototype', href: 'https://prototype.buession.com/' }
      - { name: 'Buession Shirojs', href: 'https://shirojs.buession.com/' }
    后端开源库:
      - { name: 'Buession Framework', href: 'https://www.buession.com/' }
      - { name: 'Buession Security', href: 'https://security.buession.com/' }
      - { name: 'Buession Logging', href: 'https://logging.buession.com/' }
      - { name: 'Buession Canal', href: 'https://canal.buession.com/' }
      - { name: 'Buession SpringBoot', href: 'https://springboot.buession.com/' }
      - { name: 'Buession SpringCloud', href: 'https://springcloud.buession.com/' }
      - { name: 'Buession Cas', href: 'https://cas.buession.com/' }

---

<Homepage banner={banner} features={features} />
<Footer distPath={props.page.distPath} copyRight={props.footer.copyright} links={props.footer.links} />