# fiit-ir-reddit-crawler

Reddit crawler project for IR class. http://www2.fiit.stuba.sk/~kompan/vi.html


## run docker elastic
`docker run -d --name el1 -p 9200:9200 -p 9300:9300 -v "$path_to_data":/usr/share/elasticsearch/data elasticsearch`

`docker start el1`