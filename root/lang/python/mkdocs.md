# MkDocs

## [Material for MkDocs](https://squidfunk.github.io/mkdocs-material)

### 配置

![Content area width](https://squidfunk.github.io/mkdocs-material/setup/setting-up-navigation/#content-area-width)

-   `mkdocs.css`

    ```css
    .md-grid {
      max-width: 1680px;
    }
    ```

-   `mkdocs.yaml`

    ```yaml
    site_name: '十二翼堕落天使'
    site_url: https://demo.icefery.xyz
    site_author: '教主'

    repo_name: icefery/demo.icefery.xyz
    repo_url: https://github.com/icefery/demo.icefery.xyz

    docs_dir: root
    site_dir: dist

    theme:
      name: material
      features:
        - announce.dismiss
        # - content.action.edit
        # - content.action.view
        - content.code.annotate
        - content.code.copy
        # - content.code.select
        # - content.footnote.tooltips
        # - content.tabs.link
        - content.tooltips
        # - header.autohide
        # - navigation.expand
        # - navigation.footer
        - navigation.indexes
        - navigation.instant
        - navigation.instant.prefetch
        - navigation.instant.progress
        # - navigation.prune
        - navigation.sections
        - navigation.tabs
        - navigation.tabs.sticky
        - navigation.top
        - navigation.tracking
        - search.highlight
        - search.share
        - search.suggest
        - toc.follow
        # - toc.integrate
      palette:
        - media: '(prefers-color-scheme: light)'
          scheme: default
          toggle:
            icon: material/brightness-7
        - media: '(prefers-color-scheme: dark)'
          scheme: slate
          toggle:
            icon: material/brightness-4
      font:
        text: Roboto
        code: Roboto Mono
      favicon: https://avatars.githubusercontent.com/u/61737634
      logo: https://avatars.githubusercontent.com/u/61737634
      icon:
        repo: fontawesome/brands/github

    plugins:
      - search:

    markdown_extensions:
      - abbr
      - admonition
      - attr_list
      - def_list
      - footnotes
      - md_in_html
      - toc:
          permalink: true
      - pymdownx.arithmatex:
          generic: true
      - pymdownx.betterem:
          smart_enable: all
      - pymdownx.caret
      - pymdownx.details
      - pymdownx.emoji:
          emoji_generator: !!python/name:material.extensions.emoji.to_svg
          emoji_index: !!python/name:material.extensions.emoji.twemoji
      - pymdownx.highlight:
          anchor_linenums: true
          line_spans: __span
          pygments_lang_class: true
      - pymdownx.inlinehilite
      - pymdownx.keys
      - pymdownx.magiclink:
          normalize_issue_symbols: true
          repo_url_shorthand: true
          user: squidfunk
          repo: mkdocs-material
      - pymdownx.mark
      - pymdownx.smartsymbols
      - pymdownx.snippets:
      - pymdownx.superfences:
          custom_fences:
            - name: mermaid
              class: mermaid
              format: !!python/name:pymdownx.superfences.fence_code_format
      - pymdownx.tabbed:
          alternate_style: true
          combine_header_slug: true
          slugify: !!python/object/apply:pymdownx.slugs.slugify
            kwds:
              case: lower
      - pymdownx.tasklist:
          custom_checkbox: true
      - pymdownx.tilde

    extra_css:
      - __static__/css/mkdocs.css
    ```
