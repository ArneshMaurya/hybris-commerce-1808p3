<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<template:page pageTitle="${pageTitle}">

  <div class="yCmsComponent login-right-content-component">
    <div class="login-section">
      <div class="login-page__headline">
        Returning Customer
      </div>
      <p>
        Already have an account? Sign in to retrieve your account settings.
      </p>
      <form id="loginForm" action="/trainingstorefront/en/view/myLogin"
      method="post">
        <div class="form-group">
          <label class="control-label " for="j_username">
            Email Address
          </label>
          <input id="j_username" name="j_username" class=" form-control" type="text"
          value="">
        </div>
        <div class="form-group">
          <label class="control-label " for="j_password">
            Password
          </label>
          <input id="j_password" name="j_password" class="form-control" type="password"
          value="" autocomplete="off">
        </div>
        <div class="forgotten-password">
          <a href="#" data-link="/trainingstorefront/en/login/pw/request" class="js-password-forgotten"
          data-cbox-title="Reset Password">
            Forgot your password?
          </a>
        </div>
        <button type="submit" class="btn btn-primary btn-block">
          Log In
        </button>
        <div>
          <input type="hidden" name="CSRFToken" value="82c52b86-5079-4095-8a6d-40bdcba74022">
        </div>
      </form>
    </div>
  </div>
</template:page>