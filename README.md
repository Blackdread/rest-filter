[![Build Status](https://travis-ci.org/Blackdread/rest-filter.svg?branch=master)](https://travis-ci.org/Blackdread/rest-filter)
[![Coverage Status](https://coveralls.io/repos/github/Blackdread/rest-filter/badge.svg?branch=master)](https://coveralls.io/github/Blackdread/rest-filter?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.blackdread.lib/rest-filter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.blackdread.lib/rest-filter)
[![Github Workflow](https://github.com/Blackdread/rest-filter/workflows/Java%20CI/badge.svg)](https://github.com/Blackdread/rest-filter/workflows/Java%20CI/badge.svg)

# rest-filter
Provide consistent functionality to filter/sort for the rest API.

It supports JPA criteria, jOOQ dynamic filtering and sorting.

It tries to make use of Spring Framework optional but filters already have some annotations from Spring.

If Spring Framework is used, it will be added only in specific package and dependencies will be made optional (true) to limit impact if not used.

# Filter of enum
When need to filter on an enum, just declare a class or static class like:
    
    public static class MyEnumFilter extends Filter<MyEnum> {
    
        public MyEnumFilter(){}
    
        public MyEnumFilter(final MyEnumFilter filter){super(filter);}
        
        @Override
        public MyEnumFilter copy(){return new MyEnumFilter(this);}
    }

# API use
When using Spring with REST, you would map the criteria like that:

```
@GetMapping("/my-link")
public ResponseEntity<List<MyDTO>> getAll(MyCriteria criteria, Pageable pageable) {
    Page<MyDTO> page = myQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, ...);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
}
```

The query url would look something like:

`http://myWebsite.com?id.equals=1&otherProperty.in=name,other,etc&createTime.greaterThan=2019-01-01T05:04:01Z&sort=createTime,desc&sort=id,desc`

With that the filtering and sorting would work and be consistent through the app.
