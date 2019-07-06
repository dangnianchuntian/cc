package com.zhanghan.zuul.filter;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 *
 * @author Haojun Ren
 * @version 1.0
 */

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.zhanghan.zuul.bean.GrayBean;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class GrayZuulFilter extends ZuulFilter {


    @Autowired
    private GrayBean grayBean;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        if (shouldBeRedirected()) {
            RequestContext.getCurrentContext().setSendZuulResponse(false);
            String redirectUrl = generateRedirectUrl(grayBean.getSuffix());

            sendRedirect(redirectUrl);
        }

        return null;
    }


    private void sendRedirect(String redirectUrl) {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        HttpServletRequest request = ctx.getRequest();
        try {
            if (request.getMethod().toUpperCase().equals("POST")) {
                response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            } else {
                response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
            }
            response.setHeader(HttpHeaders.LOCATION, redirectUrl);
            response.flushBuffer();
        } catch (Exception ex) {
        }
    }

    private boolean shouldBeRedirected() {

        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();


        if (grayBean.isEnabled()) {

            if (grayBean.getGraylist().isEmpty()) {
                return false;
            }

            String requestUrl = request.getRequestURI();

            if (grayBean.getGraylist().contains(requestUrl.split("/")[1])) {

                Object companyNo = getParment(request, "companyNo");

                if (null != companyNo && companyNo.toString().equals(grayBean.getCompanyNo())) {
                    return true;
                }

            }

        }
        return false;
    }

    private Object getParment(HttpServletRequest request, String parmentKey) {

        try {

            String charset = request.getCharacterEncoding();

            InputStream inputStream = request.getInputStream();

            String body = StreamUtils.copyToString(inputStream, Charset.forName(charset));

            if (StringUtils.isEmpty(body)) {
                return null;
            }

            JSONObject json = JSONObject.fromObject(body);

            return json.get(parmentKey);

        } catch (Exception e) {
            return null;
        }

    }

    private String generateRedirectUrl(String s) {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String queryParams = request.getQueryString();
        Object originalRequestPath = request.getRequestURI();
        String[] modifiedRequestPathArr = (originalRequestPath.toString().split("/", 3));
        modifiedRequestPathArr[1] = modifiedRequestPathArr[1] + s;
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : modifiedRequestPathArr) {
            if (StringUtils.isNotEmpty(str)) {
                stringBuilder.append("/");
                stringBuilder.append(str);
            }
        }
        return stringBuilder.toString() + (queryParams == null ? "" : ("?" + queryParams));
    }


}