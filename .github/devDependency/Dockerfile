FROM docker.elastic.co/elasticsearch/elasticsearch:7.15.1
RUN set -euxo; elasticsearch-plugin install --batch analysis-smartcn \
    && elasticsearch-plugin install --batch analysis-icu \
    && elasticsearch-plugin install --batch https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.15.1/elasticsearch-analysis-ik-7.15.1.zip
