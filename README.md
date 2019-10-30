[![Build Status](https://travis-ci.org/Blackdread/rest-filter.svg?branch=master)](https://travis-ci.org/Blackdread/rest-filter)
[![Coverage Status](https://coveralls.io/repos/github/Blackdread/rest-filter/badge.svg?branch=master)](https://coveralls.io/github/Blackdread/rest-filter?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.blackdread.lib/rest-filter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.blackdread.lib/rest-filter)
[![Github Workflow](https://github.com/Blackdread/rest-filter/workflows/Java%20CI/badge.svg)](https://github.com/Blackdread/rest-filter/actions?workflow=Java+CI)

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
public ResponseEntity<List<MyDTO>> getAll(@Valid MyCriteria criteria, Pageable pageable) {
    Page<MyDTO> page = myQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, ...);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
}
```

The query url would look something like:

`http://myWebsite.com?id.equals=1&otherProperty.in=name,other,etc&createTime.greaterThan=2019-01-01T05:04:01Z&sort=createTime,desc&sort=id,desc`

With that the filtering and sorting would work and be consistent through the app.

# Auto filter query param
The class [CriteriaQueryParam](https://github.com/Blackdread/rest-filter/blob/master/src/main/java/org/blackdread/lib/restfilter/criteria/CriteriaQueryParam.java) allow to create query params automatically with reflection from a criteria class.

Given a criteria like:

    public class AliasAnnotationCriteria {

        @CriteriaInclude(keyOnly = true)
        private long keyParamOnly;

        @CriteriaInclude
        @CriteriaAlias("primitiveLongAlias") // optional alias
        private long primitiveLong;
    
        @CriteriaInclude
        @CriteriaAlias("objectLongAlias")
        private Long objectLong;
    
        @CriteriaAlias("longFilterAlias")
        private LongFilter longFilter; // Filter fields do not require @CriteriaInclude
    
        @CriteriaInclude(long.class)
        @CriteriaAlias("arrayPrimitiveLongAlias")
        private long[] arrayPrimitiveLong;
    
        @CriteriaInclude(Long.class)
        @CriteriaAlias("arrayObjectLongAlias")
        private Long[] arrayObjectLong;
    
        @CriteriaInclude
        @CriteriaAlias("arrayLongFilterAlias")
        private LongFilter[] arrayLongFilter; // not supported but does not throw during parse
    
        @CriteriaAlias("arrayLongFilterNotIncludedAlias")
        private LongFilter[] arrayLongFilterNotIncluded;

        // Also can provide filters from methods
        @CriteriaInclude
        @CriteriaAlias("computed1Alias") // optional alias
        public long getComputed1() {
            return 1; // can be computed value
        }
    
        @CriteriaInclude
        @CriteriaAlias("getComputed2Alias")
        public Long getComputed2() {
            return 1L; // can be computed value
        }
    
        @CriteriaInclude
        @CriteriaAlias("getComputed3Alias")
        public LongFilter getComputed3() {
            return new LongFilter(); // can be computed value
        }
    
        // Getters and Setters
    }
    
Depending on values of fields and return value for methods annotated, the generated query params may be:
- keyParamOnly&primitiveLongAlias=1&arrayPrimitiveLongAlias=1,2&arrayLongFilterAlias.equals=5&getComputed3Alias.notIn=5,6&getComputed3Alias.greaterThan=2

- keyParamOnly&getComputed3Alias.notIn=5,6

- objectLongAlias=1&primitiveLongAlias=1

- etc

# Auto page query param
The class [PageableQueryParamSpringUtil](https://github.com/Blackdread/rest-filter/blob/master/src/main/java/org/blackdread/lib/restfilter/spring/query/PageableQueryParamSpringUtil.java) allow to create page query params automatically from Pageable class.

Generate like `page=0&size=50` or `page=10&size=500`

# Auto sort query param
The class [SortQueryParamSpringUtil](https://github.com/Blackdread/rest-filter/blob/master/src/main/java/org/blackdread/lib/restfilter/spring/query/SortQueryParamSpringUtil.java) allow to create sort query params automatically from Sort class.

Generate like `sort=id,asc0&sort=createTime,desc` or `sort=createTime,desc`
